package org.HouseProject

import redis.clients.jedis.{Jedis, JedisPool, JedisPoolConfig}
//访问redis
object JedisConnectionPool{
  val config=new JedisPoolConfig()
  var pool=new JedisPool(config,sys.env.getOrElse("REDIS_HOST", "localhost"),sys.env.getOrElse("REDIS_PORT", "6379").toInt,10000)
  def getConnection:Jedis={
    pool.getResource
  }

}
