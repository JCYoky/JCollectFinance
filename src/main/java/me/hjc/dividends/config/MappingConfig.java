package me.hjc.dividends.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "mapping-config")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
public class MappingConfig {
    private Map<String, String> urlMapping;

    private Map<Integer, Double> expMapping = new HashMap<>();

    public String getURL(String urlName) {
        return urlMapping.get(urlName);
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }
}
