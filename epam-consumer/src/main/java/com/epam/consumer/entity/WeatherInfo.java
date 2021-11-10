package com.epam.consumer.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Li Ming
 * @date 2021.2021/11/8 08:14
 */
@Data
public class WeatherInfo implements Serializable {
    private WeatherInfo weatherinfo;
    static final long serialVersionUID = 42;
//    {"weatherinfo":{"city":"苏州","cityid":"101190401","temp":"23.9","WD":"东北风","WS":"小于3级","SD":"79%","AP":"1004.9hPa","njd":"暂无实况","WSE":"<3","time":"18:00","sm":"1.5","isRadar":"0","Radar":""}}
    private String city;
    private String cityid;
    private String temp;
    private String WD;
    private String WS;
    private String SD;
    private String AP;
    private String njd;
    private String WSE;
    private String time;
    private String sm;
    private Integer isRadar;
    private String Radar;
}
