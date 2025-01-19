package com.yevmie.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    @Around("@annotation(com.yevmie.aspect.TrackExecutionTime)")
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        Object result = joinPoint.proceed();
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        String methodName = joinPoint.getSignature().toShortString();
        log.info("Metod {} {} ms'de tamamlandı", methodName, duration);

        if (duration > 1000) { // 1 saniyeden uzun süren işlemler için uyarı
            log.warn("Performans Uyarısı: {} metodu {} ms sürdü", methodName, duration);
        }

        return result;
    }
} 