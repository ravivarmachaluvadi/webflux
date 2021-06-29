package com.example.springboot.webflux.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import oracle.r2dbc.impl.OracleConnectionFactoryProviderImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

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


    @Bean
    R2dbcEntityTemplate reactiveTemplate() {

        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.HOST, "localhost")
                .option(ConnectionFactoryOptions.PORT, 1521)
                .option(ConnectionFactoryOptions.USER, "hr")
                .option(ConnectionFactoryOptions.PASSWORD, "oracle")
                .option(ConnectionFactoryOptions.DATABASE, "orcl")
                .option(ConnectionFactoryOptions.DRIVER, "oracle")
                //.option(ConnectionFactoryOptions.PROTOCOL,"driver")
                .build();

        ConnectionFactory connectionFactory = ConnectionFactories.get(options);

        return  new R2dbcEntityTemplate(connectionFactory);
    }
}

