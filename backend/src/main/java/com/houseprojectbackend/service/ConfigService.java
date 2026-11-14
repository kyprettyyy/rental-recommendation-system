package com.houseprojectbackend.service;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;
public class ConfigService {
    private static final Properties CONFIG = new Properties();
    static {
        try (InputStream stream = ConfigService.class.getResourceAsStream("/disposition.properties")) {
            if (stream == null) throw new IllegalStateException("Missing disposition.properties");
            CONFIG.load(stream);
        } catch (IOException e) { throw new ExceptionInInitializerError(e); }
    }
    public String topic() { return System.getenv().getOrDefault("KAFKA_TOPIC", CONFIG.getProperty("kafka.topic")); }
    public String key() { return System.getenv().getOrDefault("REDIS_RECOMMEND_KEY", CONFIG.getProperty("redis.hashKey")); }
    public Integer getLimitSize() { return Integer.valueOf(CONFIG.getProperty("mysql.limitSize")); }
    public String recommended_again() { return CONFIG.getProperty("historical_behavior_recommend"); }
}
