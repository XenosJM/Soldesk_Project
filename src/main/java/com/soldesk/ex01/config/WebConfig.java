package com.soldesk.ex01.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// web.xml과 동일
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer{

   // root application context(Root WebApplicationContext)에 적용하는 설정 클래스 지정 메서드 
   @Override
   protected Class<?>[] getRootConfigClasses() {
      
	   // RootConfig 클래스, Security 클래스 리턴
		return new Class[] { RootConfig.class/* , SecurityConfig.class */};  
   }

   // servlet application context(Servlet WebApplicationContext)에 적용하는 설정 클래스 지정 메서드
   @Override
   protected Class<?>[] getServletConfigClasses() {
      
      return new Class[] {ServletConfig.class}; // ServletConfig 클래스 리턴
   }

   
   // Servlet Mapping 메서드
   @Override
   protected String[] getServletMappings() {
      
      return new String[] {"/"}; // 기본 경로 리턴
   }
   
// Filter 설정 메서드
   @Override
   protected Filter[] getServletFilters() {
	   CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);
      CSPFilter cspFilter = new CSPFilter();
      return new Filter[] { encodingFilter, cspFilter };
   }
   
} // end WebConfig




