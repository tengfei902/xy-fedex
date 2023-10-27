package com.xy.fedex.catalog.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ObjectFullNameConvertAspect {

  @Around("@annotation(EnableObjectConvert)")
  public void execute(ProceedingJoinPoint joinPoint) {
    Object[] args = joinPoint.getArgs();
    for(Object arg:args) {

    }
  }
}
