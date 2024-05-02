package com.soldesk.ex01.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

// web.xml�� ����
public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer{

   // root application context(Root WebApplicationContext)�� �����ϴ� ���� Ŭ���� ���� �޼��� 
   @Override
   protected Class<?>[] getRootConfigClasses() {
      
	   // RootConfig Ŭ����, Security Ŭ���� ����
      return new Class[] {RootConfig.class}; // TODO , SecurityConfig.class ���߿� ��ť��Ƽ ����� �� ������  
   }

   // servlet application context(Servlet WebApplicationContext)�� �����ϴ� ���� Ŭ���� ���� �޼���
   @Override
   protected Class<?>[] getServletConfigClasses() {
      
      return new Class[] {ServletConfig.class}; // ServletConfig Ŭ���� ����
   }

   
   // Servlet Mapping �޼���
   @Override
   protected String[] getServletMappings() {
      
      return new String[] {"/"}; // �⺻ ��� ����
   }
   
   // Filter ���� �޼���
   @Override
   protected Filter[] getServletFilters() {
      CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
      encodingFilter.setEncoding("UTF-8");
      encodingFilter.setForceEncoding(true);
      
      return new Filter[] { encodingFilter };
   }
   

} // end WebConfig




