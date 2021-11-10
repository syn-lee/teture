package com.epam.consumer.service.impl;

import com.epam.consumer.entity.Area;
import com.epam.consumer.entity.WeatherInfo;
import com.epam.consumer.service.WeatherService;
import com.epam.consumer.util.AssertUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Li Ming
 * @date 2021.2021/11/8 21:07
 */
@Service
@Slf4j
@DependsOn("remoteWeatherService")
public class WeatherServiceImpl implements WeatherService, InitializingBean {
    @Autowired
    private RemoteWeatherService rws;

    private Cache<String, WeatherInfo> weatherCache = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES).initialCapacity(1000).build();
    private Cache<String, Area> areaCache = CacheBuilder.newBuilder().initialCapacity(1000).build();
    private volatile boolean initialed, initialing;

    @Override
    public void afterPropertiesSet() {
        initialing = true;
        new Thread(() -> {
            try {
                init();
                initialed = areaCache.size() > 0;
            } catch (Exception e) {
                log.error(getClass().getName() + " error init ", e);
            } finally {
                initialing = false;
            }
        }).start();
    }

    @Override
    public WeatherInfo checkWeather(String provinceName, String cityName, String countyName) {
        if (!initialed) {
            if (!initialing ) {
                afterPropertiesSet();
            }
            AssertUtil.isTrue(false, "area.is.initialing");
        }
        // 参数验证
        AssertUtil.notNull(countyName, "county.not.null", countyName);
        Area.County county = (Area.County) areaCache.getIfPresent(trim(countyName));
        AssertUtil.notNull(county, "county.not.valid", countyName);
        AssertUtil.isTrue(county.getCity().getName().contains(trim(cityName)), "city.not.valid", cityName);
        AssertUtil.isTrue(county.getProvince().getName().contains(trim(provinceName)), "province.not.valid", provinceName);
        // 拼装天气参数
        String code = county.getProvince().getCode() + county.getCity().getCode() + county.getCode();
        return Optional.ofNullable(weatherCache.getIfPresent(code)).orElseGet(() -> {
            WeatherInfo weather = rws.weather(code);
            if (null != weather) {
                weather = weather.getWeatherinfo();
                weatherCache.put(code, weather);
                weatherCache.put(trim(countyName), weather);
            }
            return weather;
        });
    }

    @Override
    public Area getArea(String areaName, boolean simple) {
        Area area = areaCache.getIfPresent(trim(areaName));
        AssertUtil.notNull(area, "area.not.valid", areaName);
        if (simple) {
            if (area instanceof Area.Province) {
                return new Area(area.getCode(), area.getName());
            }
            if (area instanceof Area.City) {
                Area.City city = (Area.City) area;
                return new Area(city.getProvince().getCode() + city.getCode(), city.getProvince().getName() + city.getName());
            }
            if (area instanceof Area.County) {
                Area.County county = (Area.County) area;
                return new Area(county.getProvince().getCode() + county.getCity().getCode() + county.getCode(), county.getProvince().getName() + county.getCity().getName() + county.getName());
            }

        }
        return area;
    }

    private String trim(String county) {
        String pattern = "[省市县区镇]";
        String _county = county.replaceAll(pattern, "");
        return _county.length() > 1 ? _county : county;
    }

    private void init() {
        // code = name
        Map<String, String> ps = rws.getProvince();
        ps.forEach((code, name) -> {
            Area.Province province = new Area.Province(code, name);
            initCities(province);
            areaCache.put(name, province);
        });
    }

    private void initCities(Area.Province province) {
        // code = name
        Map<String, String> cs = rws.getCity(province.getCode());
        province.setCities(cs);
        cs.forEach((code, name) -> {
            Area.City city = new Area.City(code, name);
            city.setProvince(province);
            initCounties(province, city);
            areaCache.put(name, city);
        });
    }

    private void initCounties(Area.Province province, Area.City city) {
        // code = name
        Map<String, String> counties = rws.getCounty(province.getCode() + city.getCode());
        city.setCounties(counties);
        counties.forEach((code, name) -> {
            Area.County county = new Area.County(code, name);
            county.setProvince(province);
            county.setCity(city);
            areaCache.put(name, county);
            areaCache.put(name + "code", new Area(province.getCode() + city.getCode() + code, province.getName() + city.getName() + name));
        });
    }
}
