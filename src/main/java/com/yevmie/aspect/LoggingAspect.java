package com.yevmie.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.yevmie.service.*.*(..))")
    public void forServicePackage() {}

    @Pointcut("execution(* com.yevmie.controller.*.*(..))")
    public void forControllerPackage() {}

    @Pointcut("forServicePackage() || forControllerPackage()")
    public void forAppFlow() {}

    @Before("forAppFlow()")
    public void before(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().toShortString();
        log.info("=====>> @Before: çağrılan metod: {}", method);

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.info("=====>> argüman: {}", arg);
        }
    }

    @AfterReturning(
        pointcut = "forAppFlow()",
        returning = "result"
    )
    public void afterReturning(JoinPoint joinPoint, Object result) {
        String method = joinPoint.getSignature().toShortString();
        log.info("=====>> @AfterReturning: metod: {}", method);
        log.info("=====>> sonuç: {}", result);
    }

    @AfterThrowing(
        pointcut = "forAppFlow()",
        throwing = "exc"
    )
    public void afterThrowing(JoinPoint joinPoint, Throwable exc) {
        String method = joinPoint.getSignature().toShortString();
        log.error("=====>> @AfterThrowing: metod: {}", method);
        log.error("=====>> istisna: {}", exc.getMessage());
    }
} 