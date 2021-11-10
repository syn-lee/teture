package com.epam.consumer.service.impl;

import com.epam.consumer.entity.WeatherInfo;
import feign.Contract;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @author Li Ming
 */
@FeignClient(name = "epam-provider", path = "/epam/weather", qualifier = "remoteWeatherService")
public interface RemoteWeatherService {

    @GetMapping("/city/{province}")
    Map<String, String> getCity(@PathVariable("province") String province);

    @GetMapping("/county/{city}")
    Map<String, String> getCounty(@PathVariable("city") String city);

    @GetMapping("/provinces")
    Map<String, String> getProvince();

    @GetMapping("/{code}")
    WeatherInfo weather(@PathVariable("code") String code);
}

//@Configuration
class
FeignConfig {
    @Bean
    Contract contract() {
        return new feign.Contract.Default();
    }
}
