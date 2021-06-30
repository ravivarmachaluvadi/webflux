package com.example.springboot.webflux.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "oraEntityManagerFactory", transactionManagerRef = "entityTransactionManager", basePackages = {
        "com.example.springboot.webflux.repository" })
public class OracleDBConfig {

    @Bean(name = "oraDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.oracle.hikari")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "oraEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean oraEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                           @Qualifier("oraDataSource") DataSource dataSource) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update"); //overriding
        properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle12cDialect");
        return builder.dataSource(dataSource).properties(properties)
                .packages("com.example.springboot.webflux.entity").persistenceUnit("entity").build();
    }

    @Bean(name = "entityTransactionManager")
    public PlatformTransactionManager entityTransactionManager(
            @Qualifier("oraEntityManagerFactory") EntityManagerFactory oraEntityManagerFactory) {
        return new JpaTransactionManager(oraEntityManagerFactory);
    }
}
