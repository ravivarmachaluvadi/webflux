package com.example.springboot.webflux.config;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

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

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    WebClient webClient() {
        return WebClient
            .builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            //.resolver(DefaultAddressResolverGroup.INSTANCE)
            .build();
    }

}
