package com.epam.provider.service;

/**
 * @author Li Ming
 * @date 2021.2021/11/8 08:06
 */
public interface WeatherService {

    String getCity(String province);

    String getCounty(String city);

    String getProvince();

    String weather(String code);
}
