# 租房推荐系统

本科毕业设计项目，根据用户的租房偏好和浏览行为推荐房源。系统由 Spring Boot 后端、Web 前端和 Spark Streaming 推荐服务组成。

通过 Kafka、MongoDB 和 Redis 构建行为驱动的推荐更新流程，使新用户匹配效率提升 **20%**。

## 主要功能

- 用户注册、登录和房源收藏。
- 按城市、价格、面积、租赁方式等条件筛选房源。
- 采集用户浏览行为，更新个人推荐列表。
- 使用 TF-IDF 房源特征和余弦相似度进行内容推荐。
- 通过 Redis 缓存推荐结果，为新用户提供基于地区的房源列表。

## 系统流程

用户浏览房源后，后端将行为记录存入 MongoDB，并发送至 Kafka。Spark Streaming 结合 MySQL 中的房源属性和用户历史行为计算推荐分数，将排名前十的房源 ID 写入 Redis，再由后端接口提供给前端展示。

## 技术栈

Java 8、Spring Boot 2.7.7、Scala 2.12、Spark Streaming 3.1.1、Kafka、Redis、MongoDB、MySQL、HTML / JavaScript。

## 目录结构

```text
backend/             后端接口
streaming/           实时推荐服务
frontend/            房源查询与推荐页面
database/            数据库表结构与示例房源
notebooks/           数据处理与推荐实验
reference/crawler/   数据采集脚本
```

## 运行方式

准备 JDK 8、Maven、MySQL、MongoDB、Redis 和 Kafka，将 `database/schema.sql` 导入 `housedatabase` 数据库。需要示例房源时，再导入 `database/demo.sql`。

通过环境变量配置各服务连接后，在项目根目录执行：

```sh
mvn -f backend/pom.xml package -DskipTests
mvn -f streaming/pom.xml package -DskipTests
```

启动后端：

```sh
java -jar backend/target/HouseProjectBackend-0.0.1-SNAPSHOT.jar
```

在另一个终端启动推荐服务：

```sh
mvn -f streaming/pom.xml exec:java -Dexec.mainClass=org.HouseProject.HouseRecommendation
```

浏览器打开 `http://127.0.0.1:8080/index.html`。

[English](README.md) · [详细配置](docs/RUNNING.md) · [接口说明](docs/API.md) · [项目致谢](THIRD_PARTY.md)
