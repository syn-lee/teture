package com.epam.consumer;

import com.epam.consumer.config.FrequencyFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * nacos-customer
 */
@SpringBootApplication
@EnableFeignClients
public class ConsumerApp {


    public static void main(String[] args) {
        SpringApplication.run(ConsumerApp.class, args);
    }

    @Bean
    FrequencyFilter frequencyFilter(){
        return new FrequencyFilter();
    }

}
