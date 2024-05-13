package com.soldesk.ex01.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

// ��� �޼��忡 �α� ����
@Aspect
@Component
@Log4j
public class LoggingAspect {
   //   * Pointcut�� �����ϴ� ���2
   // @Before, @afterReturning, @afterThrowing, @after 
   
    @Before("execution(* com.soldesk.ex01.controller.*.*(..))")
    // com.mokcoding.ex03 ��Ű���� ���Ե� ��� Ŭ������ ��� �޼���
    public void beforeAdvice(JoinPoint joinPoint) {
       // JoinPoint : Advice�� ����� �޼��忡 ���� ����
        String methodName = joinPoint.getSignature().getName(); // �޼��� �̸�
        String className = joinPoint.getTarget()
              .getClass().getSimpleName(); // Ŭ���� �̸�
        log.info("before : " + className + "." + methodName + "()");
    } // beforeAdvice()

    @After("execution(* com.soldesk.ex01.controller.*.*(..))")
    public void afterAdvice(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.info("after : " + className + "." + methodName + "()");
    } // afterAdvice()
    
    
    

} // end LoggingAspect