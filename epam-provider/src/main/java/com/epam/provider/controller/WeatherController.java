package com.epam.provider.controller;

import com.epam.provider.service.WeatherService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liming
 */
@Slf4j
@RestController
@RequestMapping("/weather")
@AllArgsConstructor
public class WeatherController {

    private WeatherService weatherService;

    @GetMapping(value = "/provinces", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getProvince() {
        return weatherService.getProvince();
    }

    @GetMapping(value = "/city/{code}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getCity(@PathVariable("code") String province) {
        return weatherService.getCity(province);
    }

    @GetMapping(value = "/county/{code}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String getCounty(@PathVariable("code") String city) {
        return weatherService.getCounty(city);
    }

    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String weather(@PathVariable("code") String code) {
        return weatherService.weather(code);
    }
}
