package com.example.workflow.configuration;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public RequestInterceptor feignRequestInterceptor() {
        return requestTemplate -> {
            String url = requestTemplate.request().url(); // Lấy URL gốc
            String decodedUrl = url.replace("%3A", ":"); // Thay thế %3A thành :
            requestTemplate.uri(decodedUrl);
        };
    }
}
