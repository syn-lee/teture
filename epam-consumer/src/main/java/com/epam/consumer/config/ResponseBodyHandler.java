package com.epam.consumer.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.util.ClassUtil;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * 拦截Controller方法默认返回参数，统一处理返回值/响应体
 *
 * @author Li Ming
 */
@ControllerAdvice
public class ResponseBodyHandler implements ResponseBodyAdvice {

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (serverHttpResponse instanceof ServletServerHttpResponse) {
            if (!ClassUtils.isPrimitiveOrWrapper(o.getClass()) && !(o instanceof CharSequence) || o.toString().startsWith("{")) {
                o = JSON.parseObject(JSON.toJSONString(o, SerializerFeature.NotWriteDefaultValue));
            }
            HttpServletResponse response = ((ServletServerHttpResponse) serverHttpResponse).getServletResponse();
            Result obj;
            if (ClassUtils.isPrimitiveOrWrapper(o.getClass()) || response.getStatus() >= 200 && response.getStatus() < 300) {
                obj = Result.success(o);
            } else {
                obj = Result.error(response.getStatus(), "", o);
            }
            return obj;
        }
        return o;
    }


    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }
}
