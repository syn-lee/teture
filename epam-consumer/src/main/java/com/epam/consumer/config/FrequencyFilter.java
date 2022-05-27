package com.epam.consumer.config;

import com.epam.consumer.util.AssertUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author Li Ming
 */
@Slf4j
public class FrequencyFilter extends OncePerRequestFilter {

    @Value("${service.limit:100}")
    public Integer frequency;
    private Cache<String, Integer> limiter = CacheBuilder.newBuilder().concurrencyLevel(1000).expireAfterWrite(1, TimeUnit.SECONDS).initialCapacity(10000).build();

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String requestMethod = request.getRequestURI();
        Integer integer = limiter.get(requestMethod, () -> 0);
        limiter.put(requestMethod, integer + 1);
        Integer count = limiter.getIfPresent(requestMethod);
        log.info("monitor method {} requested {}/s", requestMethod, count);
        AssertUtil.isTrue(count <= frequency, "server.busy");
        filterChain.doFilter(request, response);
    }
}
