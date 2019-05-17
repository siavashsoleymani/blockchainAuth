package com.mira;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {
        SecurityAutoConfiguration.class
})
public class UidOnBlockChain {

    @Value("${baseUrl}")
    private String baseUrl;

    public static void main(String[] args) {
        SpringApplication.run(UidOnBlockChain.class, args);
    }

    @Bean(name = "baseUrl")
    public String getBaseUrl() {
        return baseUrl;
    }
}
