package com.yevmie.aspect;

import java.time.LocalDateTime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.yevmie.model.AuditLog;
import com.yevmie.repository.AuditLogRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditLogRepository auditLogRepository;

    @Pointcut("@annotation(com.yevmie.aspect.Audited)")
    public void auditedMethods() {}

    @Around("auditedMethods()")
    public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication != null ? authentication.getName() : "anonymous";

        AuditLog auditLog = new AuditLog();
        auditLog.setUsername(username);
        auditLog.setMethodName(methodName);
        auditLog.setClassName(className);
        auditLog.setTimestamp(LocalDateTime.now());

        try {
            Object result = joinPoint.proceed();
            auditLog.setSuccess(true);
            return result;
        } catch (Exception e) {
            auditLog.setSuccess(false);
            auditLog.setErrorMessage(e.getMessage());
            throw e;
        } finally {
            auditLogRepository.save(auditLog);
        }
    }
} 