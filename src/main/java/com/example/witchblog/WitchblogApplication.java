package com.example.witchblog;

import com.example.witchblog.configuration.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class WitchblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(WitchblogApplication.class, args);
    }

}
