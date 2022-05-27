package com.epam.consumer.controller;

import com.epam.consumer.entity.Area;
import com.epam.consumer.entity.WeatherInfo;
import com.epam.consumer.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author liming
 * @description:
 */
@Slf4j
@RestController
@RequestMapping("/epam")
public class TestController {

    @Autowired
    private WeatherService weatherService;

    @RequestMapping("/area/{name}")
    public Area getArea(@PathVariable("name") String name, Boolean all) {
        return weatherService.getArea(name, !Boolean.TRUE.equals(all));
    }

    @RequestMapping(value = "/{province}/{city}/{county}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String test(@PathVariable("province") String province, @PathVariable("city") String city, @PathVariable("county") String county) {
        Optional<String> temperature = weatherService.getTemperature(province, city, county);
        return temperature.get();
    }

    @RequestMapping("/weather/{province}/{city}/{county}")
    public WeatherInfo weather(@PathVariable("province") String province, @PathVariable("city") String city, @PathVariable("county") String county) {
        return weatherService.checkWeather(province, city, county);
    }
    
    @RequestMapping("/weather/{code}")
    public WeatherInfo weather(@PathVariable("code") String code) {
        return weatherService.getWeatherInfo(code, null);
    }
}
