package com.decathlon.usecase.aop;

import java.util.Arrays;
import java.util.Optional;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * class for logging messages using aop
 * 
 * @author aritr
 * 
 *
 */
@Component
@Aspect
@Slf4j
public class LoggingAspect {

	@Pointcut("within(com.decathlon.usecase.service.impl.*) && execution(public * *(..))")
	public void allServiceMethods() {
	}

	@Pointcut("within(com.decathlon.usecase.controller.*) && execution(public * *(..))")
	public void allControllerMethods() {
	}

	@Pointcut("allServiceMethods() || allControllerMethods()")
	public void allControllerAndServiceMethods() {
	}

	/**
	 * method to log all controller and service method invocations with respective
	 * parameters
	 * 
	 * @param joinPoint
	 * 
	 */
	@Before("allControllerAndServiceMethods()")
	public void commonBeforeAdvice(JoinPoint joinPoint) {
		log.info("before execution: class: {} ,method : {}, args : {}", joinPoint.getTarget().getClass().getName(),
				joinPoint.getSignature().getName(),
				Arrays.stream(joinPoint.getArgs()).reduce("", (a, b) -> a.toString() + "," + b.toString()));
	}

	@AfterReturning(pointcut = "allControllerAndServiceMethods()", returning = "returnedObj")
	public void commonAfterAdvice(JoinPoint joinPoint, Object returnedObj) {
		if (Optional.ofNullable(returnedObj).isPresent())
			log.info("after execution: return value: {}", returnedObj.toString());
	}

	@AfterThrowing(pointcut = "allControllerAndServiceMethods()", throwing = "ex")
	public void commmonExceptionAdvice(JoinPoint joinPoint, RuntimeException ex) {
		log.error("after exception: ", ex.getMessage());
	}

}
