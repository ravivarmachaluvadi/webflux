/*
package com.example.springboot.webflux.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories(basePackages = "com.example.springboot.webflux.reactive.repository")
class ApplicationConfig extends AbstractR2dbcConfiguration {

  @Override
  public ConnectionFactory connectionFactory() {
    ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
            .option(ConnectionFactoryOptions.HOST, "localhost")
            .option(ConnectionFactoryOptions.PORT, 1521)
            .option(ConnectionFactoryOptions.USER, "hr")
            .option(ConnectionFactoryOptions.PASSWORD, "oracle")
            .option(ConnectionFactoryOptions.DATABASE, "orcl")
            .option(ConnectionFactoryOptions.DRIVER, "oracle")
            //.option(ConnectionFactoryOptions.PROTOCOL,"driver")
            .build();

    return ConnectionFactories.get(options);

  }
}
*/
