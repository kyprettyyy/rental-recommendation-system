package com.houseprojectbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    /**
     * 跨域配置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // /**：表示所有接口都允许跨域请求。
        registry.addMapping("/**") 
                //allowCredentials(true)：表示允许发送 Cookie。
                .allowCredentials(true)
                //allowedOriginPatterns("*")：表示支持所有域的跨域请求。
                .allowedOrigins("http://localhost:8080", "http://127.0.0.1:8080")
                //allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})：表示支持 GET、POST、PUT 和 DELETE 请求。
                .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"}) // 支持方法
                //allowedHeaders("*")：表示允许所有请求头。
                .allowedHeaders("*")
                //exposedHeaders("*")：表示允许所有响应头。
                .exposedHeaders("*");
    }

}
