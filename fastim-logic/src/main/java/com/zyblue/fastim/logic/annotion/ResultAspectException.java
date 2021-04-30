package com.zyblue.fastim.logic.annotion;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author will
 * @date 2021/4/29 9:31
 */
@Aspect
@Component
public class ResultAspectException {
    @Around("@annotation(ResultHandler)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
        } catch (Exception e) {
            //return Result.fail(e.getMessage());
        }
        return proceed;
    }
}
