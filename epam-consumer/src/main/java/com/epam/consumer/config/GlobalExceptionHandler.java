package com.epam.consumer.config;

import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Li Ming
 * @date 2021.2021/7/1 15:50
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public Object runtimeException(HttpServletResponse response, RuntimeException e){
        response.setStatus(500);
        log.debug("runtime:{}", ExceptionUtils.getStackTrace(e));
		return e.getMessage();
	}

	@ExceptionHandler(Throwable.class)
	public Object throwable(HttpServletResponse response, Throwable e) {
        log.debug("other:{}", ExceptionUtils.getStackTrace(e));
        response.setStatus(501);
		return e.getMessage();
	}
}
