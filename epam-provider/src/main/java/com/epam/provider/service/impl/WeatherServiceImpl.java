package com.epam.provider.service.impl;

import com.epam.provider.config.UrlConfig;
import com.epam.provider.service.WeatherService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author Li Ming
 */
@Service
@Slf4j
@RefreshScope
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private UrlConfig sc;
    @Value("${weather.retries:3}")
    private int retryCount;

    @Value("${weather.timeout:10}")
    private int timeout;

    private OkHttpClient.Builder okFactory = new OkHttpClient().newBuilder().connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(chain -> {
                int retries = retryCount;
                Response response;
                while (!(response = chain.proceed(chain.request())).isSuccessful()) {
                    if (--retries <= 0)
                        break;
                }
                return response;
            });

    @Override
    public String getCity(String province) {
        return response(sc.getCityUrl().replace("{id}", province));
    }

    @Override
    public String getCounty(String city) {
        return response(sc.getCountyUrl().replace("{id}", city));
    }

    @Override
    public String getProvince() {
        return response(sc.getProvinceUrl());
    }

    @SneakyThrows
    private String response(String url) {
        OkHttpClient client = okFactory.build();
        Request request = new Request.Builder().url(url).addHeader("Accept-Encoding", "identity").build();
        Response response = client.newCall(request).execute();
        try {
            String json = response.body().string();
            log.info("response....{}", json);
            return json;
        } finally {
            response.close();
        }
    }

    @Override
    @SneakyThrows
    public String weather(String code) {
        return response(sc.getUrl().replace("{id}", code));
    }
}
