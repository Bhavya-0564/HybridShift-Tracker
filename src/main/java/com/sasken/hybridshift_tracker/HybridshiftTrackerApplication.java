package com.sasken.hybridshift_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.sasken")
@EnableJpaRepositories(basePackages = "com.sasken.repository")
@EntityScan(basePackages = "com.sasken.model") 
public class HybridshiftTrackerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HybridshiftTrackerApplication.class, args);
    }
}
