package com.epam.consumer.config;

import com.epam.consumer.annotation.Limiter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author Li Ming
 * @date 2021.2021/7/1 15:16
 */
@Aspect
@Slf4j
public class AspectAdvice {

	//	@Before(value = "execution(* com.epam.service..*Impl.*(pub.syn.service.common.Pageable+))")

	@Around(value = "@annotation(remote) || @within(remote)")
	@SneakyThrows
	public Object exec(ProceedingJoinPoint proceedingJoinPoint, Limiter remote) {
		System.out.println("proceedingJoinPoint = " + proceedingJoinPoint);
		return proceedingJoinPoint.proceed();
	}
}
