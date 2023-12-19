package com.example.Habr_Parser_Project.configuration;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Bean
    public DataSource dataSource(){
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5432/SpringSceurity")
                .driverClassName("org.postgresql.Driver")
                .username("postgres")
                .password("admin")
                .build();
    }
}
