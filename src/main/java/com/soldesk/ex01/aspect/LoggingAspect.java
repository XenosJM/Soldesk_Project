package com.soldesk.ex01.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

// 모든 메서드에 로깅 적용
@Aspect
@Component
@Log4j
public class LoggingAspect {
   //   * Pointcut을 지정하는 방법2
   // @Before, @afterReturning, @afterThrowing, @after 
   
    @Before("execution(* com.soldesk.ex01.controller.*.*(..))")
    // com.mokcoding.ex03 패키지에 포함된 모든 클래스의 모든 메서드
    public void beforeAdvice(JoinPoint joinPoint) {
       // JoinPoint : Advice가 적용된 메서드에 대한 정보
        String methodName = joinPoint.getSignature().getName(); // 메서드 이름
        String className = joinPoint.getTarget()
              .getClass().getSimpleName(); // 클래스 이름
        log.info("before : " + className + "." + methodName + "()");
    } // beforeAdvice()

    @After("execution(* com.soldesk.ex01.controller.*.*(..))")
    public void afterAdvice(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.info("after : " + className + "." + methodName + "()");
    } // afterAdvice()
    
    
    

} // end LoggingAspect