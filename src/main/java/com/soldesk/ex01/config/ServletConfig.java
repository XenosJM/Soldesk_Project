package com.soldesk.ex01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import com.soldesk.ex01.handler.PrivateChatHandler;


//servlet-context.xml과 동일 
@Configuration // Spring Container에서 관리하는 설정 클래스
@EnableWebMvc // Spring MVC 기능 사용
@EnableScheduling // 스케줄링 기능 사용
@ComponentScan(basePackages = {"com.soldesk.ex01"}) // component scan 설정
public class ServletConfig implements WebMvcConfigurer {

   // ViewResolver ���� �޼���
   @Override
   public void configureViewResolvers(ViewResolverRegistry registry) {
      // InternalResourceViewResolver ���� �� ����
      InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
      viewResolver.setPrefix("/WEB-INF/views/");
      viewResolver.setSuffix(".jsp");
      registry.viewResolver(viewResolver);
   }

   // ResourceHandlers ���� �޼���
   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      // resources ���丮 ����
      registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
   }
   
   // ������ ������ ��� bean ����, �� ��ο� ������ DB���� ���ϸ� ����
   @Bean
   public String uploadPath() {
	   return "C:\\upload\\ex01";
   }
   
   // MultipartResolver bean ����
   @Bean
   public CommonsMultipartResolver multipartResolver() {
      CommonsMultipartResolver resolver = new CommonsMultipartResolver();

      // Ŭ���̾�Ʈ�� ���ε��ϴ� ��û�� ��ü ũ�� (bytes)
      resolver.setMaxUploadSize(31457280); // 10MB �� 

      // Ŭ���̾�Ʈ�� ���ε��ϴ� �� ������ �ִ� ũ�� (bytes)
      resolver.setMaxUploadSizePerFile(10485760); // 10MB
      resolver.setDefaultEncoding("UTF-8");
      
      return resolver;
   }
	
	
} // end ServletConfig




