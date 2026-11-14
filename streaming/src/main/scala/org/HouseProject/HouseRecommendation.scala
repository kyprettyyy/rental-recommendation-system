package org.HouseProject

import java.util.Properties
import com.fasterxml.jackson.databind.ObjectMapper
import com.mongodb.spark.MongoSpark
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.ml.feature.{HashingTF, IDF, RegexTokenizer}
import org.apache.spark.ml.linalg.{Vector, Vectors}
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}

/** Original content-based pipeline, repaired and parameterized in November 2026. */
object HouseRecommendation {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("HouseRecommendation")
      .master(sys.env.getOrElse("SPARK_MASTER", "local[*]"))
      .config("spark.mongodb.input.uri", sys.env.getOrElse("MONGODB_URI", "mongodb://localhost:27017/test_collection.userBehavior"))
      .getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    import spark.implicits._
    val properties = new Properties()
    properties.setProperty("user", sys.env.getOrElse("MYSQL_USER", "rent"))
    properties.setProperty("password", sys.env.getOrElse("MYSQL_PASSWORD", ""))
    val houses = spark.read.jdbc(sys.env.getOrElse("MYSQL_URL", "jdbc:mysql://localhost:3306/housedatabase"), "housetable", properties)
      .withColumn("features", concat_ws(" ", $"houseTitle", $"houseCity", $"houseAdvange", $"houseRentForm", $"houseOrientation", $"houseBuildingArea", $"province", $"houseSupporting"))
      .select($"id", $"houseTitle".as("name"), $"features", $"province").cache()
    require(houses.take(1).nonEmpty, "Import house records before starting the worker")
    val tokenizer = new RegexTokenizer().setInputCol("features").setOutputCol("words").setPattern("[^\\p{L}\\p{N}]+")
    val hashing = new HashingTF().setInputCol("words").setOutputCol("rawFeatures").setNumFeatures(150)
    val raw = hashing.transform(tokenizer.transform(houses))
    val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features_tf").fit(raw)
    val indexed = idf.transform(raw).cache()
    val cosine = udf((features: Vector, preference: Seq[Double]) => {
      val other = Vectors.dense(preference.toArray)
      val norm = Vectors.norm(features, 2)*Vectors.norm(other, 2)
      if (norm == 0) 0.0 else features.dot(other)/norm
    })
    val params: Map[String, Object] = Map(
      "bootstrap.servers" -> sys.env.getOrElse("KAFKA_BOOTSTRAP_SERVERS", "localhost:9092"),
      "key.deserializer" -> classOf[StringDeserializer], "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "rental-recommendation-worker", "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> java.lang.Boolean.TRUE)
    val context = new StreamingContext(spark.sparkContext, Seconds(1))
    val stream = KafkaUtils.createDirectStream[String, String](context, LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](Array(sys.env.getOrElse("KAFKA_TOPIC", "test")), params))
    stream.map(_.value()).foreachRDD { rdd =>
      if (!rdd.isEmpty()) {
        val connection = JedisConnectionPool.getConnection
        try {
          val password = sys.env.getOrElse("REDIS_PASSWORD", "")
          if (password.nonEmpty) connection.auth(password)
          val mapper = new ObjectMapper()
          rdd.toLocalIterator.foreach { message =>
            val parts = message.split(",", 2)
            if (parts.length == 2 && parts(0).matches("[0-9]+")) {
              val user = parts(0)
              // Always scope MongoDB history to this user, even with zero or one event.
              val mongo = MongoSpark.load(spark)
              val history = if (mongo.columns.contains("userId") && mongo.columns.contains("goods")) {
                mongo.filter(col("userId").cast("string") === lit(user))
                  .select(col("goods").cast("int").as("id")).na.drop().distinct()
              } else spark.emptyDataset[Int].toDF("id")
              val consumed = history.join(houses, Seq("id"))
              val historyText = consumed.agg(concat_ws(" ", collect_list($"features")).as("text")).head().getString(0)
              val selected = houses.filter(col("id").cast("string") === lit(parts(1))).take(1)
              val currentText = selected.headOption.map(_.getAs[String]("features")).getOrElse(parts(1))
              val text = if (parts(1) == "history") historyText else currentText
              val input = Seq(Option(text).getOrElse("")).toDF("features")
              val vector = idf.transform(hashing.transform(tokenizer.transform(input)))
                .select("features_tf").head().getAs[Vector](0).toArray
              var candidates = indexed.join(history, Seq("id"), "left_anti")
              selected.headOption.flatMap(row => Option(row.getAs[String]("province"))).foreach { province =>
                candidates = candidates.filter(col("province") === lit(province))
              }
              val ids = candidates.withColumn("similarity", cosine($"features_tf", typedLit(vector.toSeq)))
                .orderBy(col("similarity").desc, col("id").asc).limit(10).select("id")
                .collect().map(_.getAs[Int](0))
              connection.hset(sys.env.getOrElse("REDIS_RECOMMEND_KEY", "recommend"), user, mapper.writeValueAsString(ids))
            }
          }
        } finally connection.close()
      }
    }
    context.start()
    context.awaitTermination()
  }
}
