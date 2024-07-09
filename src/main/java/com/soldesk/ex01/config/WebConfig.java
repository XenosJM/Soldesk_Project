package com.soldesk.ex01.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// web.xml과 동일한 역할을 하는 클래스
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer{

   // root application context(Root WebApplicationContext)에 적용하는 설정 클래스 지정 메서드 
   @Override
   protected Class<?>[] getRootConfigClasses() {
       // 리턴하는(적용하는) 클래스 명시
       return new Class[] { RootConfig.class, SecurityConfig.class };
   }

   // servlet application context(Servlet WebApplicationContext)에 적용하는 설정 클래스 지정 메서드
   @Override
   protected Class<?>[] getServletConfigClasses() {
       // 리턴하는(적용하는) 클래스 명시
       return new Class[] {ServletConfig.class}; 
   }

   // DispatcherServlet의 매핑 정보를 설정하는 메서드
   @Override
   protected String[] getServletMappings() {
       // 기본 경로를 리턴하여 모든 요청을 DispatcherServlet이 처리하도록 설정
       return new String[] {"/"};
   }
   
   // 서블릿 필터 설정 메서드
   @Override
   protected Filter[] getServletFilters() {
       // CharacterEncodingFilter 설정: 모든 요청과 응답의 인코딩을 UTF-8로 설정
       CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
       encodingFilter.setEncoding("UTF-8");
       encodingFilter.setForceEncoding(true);
       
       // CSPFilter 설정: Content Security Policy(CSP) 필터를 추가
       CSPFilter cspFilter = new CSPFilter();
       
       // 필터 배열을 리턴하여 DispatcherServlet에 적용
       return new Filter[] { encodingFilter, cspFilter };
   }
   
} // end WebConfig
