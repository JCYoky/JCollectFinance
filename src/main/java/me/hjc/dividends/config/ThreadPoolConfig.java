package me.hjc.dividends.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ThreadPoolConfig extends DataSourceAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource hikariDataSource(){
        return new HikariDataSource();
    }
}
