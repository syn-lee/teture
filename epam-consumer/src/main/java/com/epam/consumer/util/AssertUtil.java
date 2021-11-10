package com.epam.consumer.util;

import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Li Ming
 * @date 2021.2021/11/8 21:14
 */
@Component
public class AssertUtil {
    private static MessageSource messageSource;

    public AssertUtil(MessageSource messageSource) {
        AssertUtil.messageSource = messageSource;
    }

    public static void notNull(Object obj, String code, Object... args) {
        if (null == obj) {
            throw new NullPointerException(msg(code, args));
        }
    }

    public static void isTrue(Boolean value, String code, Object... args) {
        if (!Boolean.TRUE.equals(value)) {
            throw new IllegalArgumentException(msg(code, args));
        }
    }

    @SneakyThrows
    public static String msg(String code, Object... args) {
        if (null == messageSource) {
            while (null == messageSource) {
                TimeUnit.MILLISECONDS.sleep(50);
            }
        }
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
