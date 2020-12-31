package com.inspire12.homepage.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
@Component
public class UserLevelAspect {

    @Around("@annotation(com.inspire12.homepage.interceptor.MethodAllowLevel)")
    public Object allow(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
        MethodAllowLevel methodAllowLevel = method.getAnnotation(MethodAllowLevel.class);
        return joinPoint.proceed();
    }
}
