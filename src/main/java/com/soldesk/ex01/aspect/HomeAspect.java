package com.soldesk.ex01.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Component // Proxy ��ü�� ����(injection) �ϱ� ���� ����
@Aspect
@Log4j
public class HomeAspect {
	
	// * Aspect
	// - �Ϲ������� �޼ҵ忡 Ư�� ����� �����ų �� ���
	
	// * Pointcut�� �����ϴ� ���1
	// - @Pointcut ������̼� �ȿ��� ����
	// - ������ Pointcut�� �ݺ��Ǵ� ���� ���� �� ����
	// - �� �� ������ Pointcut�� ���� advice �޼ҵ忡�� ����
	
//	@Pointcut("execution(* com.soldesk.ex01.HomeController.home(..))")
//	// HomeController�� home() �޼��忡 ����Ʈ�� ����
//	public void pcHome() {} // ����Ʈ�� ��ġ ����
//	
//	@Around("pcHome()") // ����Ʈ�� �޼��带 ����
//	public Object homeAdvice(ProceedingJoinPoint jp) {
//		// ProceedingJoinPoint :
//		// Advice�� ����� �޼��带 ȣ���ϰ� ȣ��� �޼����� ������
//		// ��� �����ϴ� ����� ����
//		
//		Object result = null;
//		
//		log.info("===== homeAdvice");
//		
//		try {
//			log.info("===== home() ȣ�� ��"); // @before
//			result = jp.proceed(); // HomeController.home() ����
//			log.info("===== home() ���� ��"); // @afterReturning
//		} catch (Throwable e) {
//			// @afterThrowing
//			log.info("===== ���� �߻� : " + e.getMessage());
//		} finally {
//			// @after
//			log.info("===== finally");
//		}
//		
//		return result;
//	}
}
