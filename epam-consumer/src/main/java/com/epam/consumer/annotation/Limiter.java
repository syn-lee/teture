package com.epam.consumer.annotation;

import java.lang.annotation.*;

/**
 * @author Li Ming
 * @date 2021.2021/11/8 09:20
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Limiter {
    /**
     * 每秒次数
     */
    int frequency() default 60;
}
