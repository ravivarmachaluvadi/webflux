package com.example.springboot.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class config {

    @Bean
    public Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(250);
        executor.setThreadNamePrefix("my-thread");
        //executor.setMaxPoolSize(250);
        //executor.setQueueCapacity(10000);
        executor.initialize();
        return executor;
    }
}
