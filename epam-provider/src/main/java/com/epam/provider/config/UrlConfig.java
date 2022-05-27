package com.epam.provider.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Li Ming
 * @date 2021.2021/11/8 08:38
 */
@ConfigurationProperties(prefix = "weather")
@Component
@Data
@RefreshScope
public class UrlConfig {
    private String provinceUrl = "http://www.weather.com.cn/data/city3jdata/china.html";
    private String cityUrl = "http://www.weather.com.cn/data/city3jdata/provshi/{id}.html";
    private String countyUrl = "http://www.weather.com.cn/data/city3jdata/station/{id}.html";
    private String url = "http://www.weather.com.cn/data/sk/{id}.html";
    
    public void setProvinceUrl(String provinceUrl) {
        this.provinceUrl = provinceUrl;
    }
}
