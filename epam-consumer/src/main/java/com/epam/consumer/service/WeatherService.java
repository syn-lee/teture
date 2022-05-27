package com.epam.consumer.service;

import com.epam.consumer.entity.Area;
import com.epam.consumer.entity.WeatherInfo;
import com.epam.consumer.util.AssertUtil;

import java.util.Optional;

/**
 * @author Li Ming
 * @date 2021.2021/11/8 21:07
 */
public interface WeatherService {
    /**
     * @param province
     * @param city
     * @param county
     * @return java.util.Optional<java.lang.Integer>
     * @author Li Ming
     */
    default Optional<String> getTemperature(String province, String city, String county) {
        WeatherInfo weatherInfo = checkWeather(province, city, county);
        AssertUtil.notNull(weatherInfo, "weather.not.valid");
        return Optional.ofNullable(weatherInfo.getTemp());
    }

    /**
     * @param province
     * @param city
     * @param county
     * @return com.epam.consumer.entity.WeatherInfo
     * @author Li Ming
     */
    WeatherInfo checkWeather(String province, String city, String county);
    
    WeatherInfo getWeatherInfo(String code, String countyName);
    
    Area getArea(String areaName, boolean onlyPath);
}
