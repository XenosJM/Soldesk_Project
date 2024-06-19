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

import com.soldesk.ex01.util.PrivateChatHandler;


// servlet-context.xml과 동일 
@Configuration // Spring Container에서 관리하는 설정 클래스
@EnableWebMvc // Spring MVC 기능 사용
//@EnableWebSocket // 웹소켓 활성화
@EnableScheduling // 스케줄링 기능 사용
@ComponentScan(basePackages = {"com.soldesk.ex01"}) // component scan 설정
public class ServletConfig implements WebMvcConfigurer, WebSocketConfigurer {

   // ViewResolver 설정 메서드
   @Override
   public void configureViewResolvers(ViewResolverRegistry registry) {
      // InternalResourceViewResolver 생성 및 설정
      InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
      viewResolver.setPrefix("/WEB-INF/views/");
      viewResolver.setSuffix(".jsp");
      registry.viewResolver(viewResolver);
   }

   // ResourceHandlers 설정 메서드
   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      // resources 디렉토리 설정
      registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
   }
   
   // 파일을 저장할 경로 bean 생성, 이 경로에 저장후 DB에는 파일명만 저장
   @Bean
   public String uploadPath() {
	   return "C:\\upload\\ex01";
   }
   
   // MultipartResolver bean 생성
   @Bean
   public CommonsMultipartResolver multipartResolver() {
      CommonsMultipartResolver resolver = new CommonsMultipartResolver();

      // 클라이언트가 업로드하는 요청의 전체 크기 (bytes)
      resolver.setMaxUploadSize(31457280); // 10MB 각 

      // 클라이언트가 업로드하는 각 파일의 최대 크기 (bytes)
      resolver.setMaxUploadSizePerFile(10485760); // 10MB
      resolver.setDefaultEncoding("UTF-8");
      
      return resolver;
   }
   
   // 웹 소켓 연결 설정
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(privateChatHandler(), "/private") // 연결 호출
	    		.setAllowedOrigins("*"); // 허가된 도메인에서의 연결만 허용 현재는 테스트라 모두 허용
		
	}
	// 웹 소켓 연결에 필요한 빈 생성
	@Bean
	public PrivateChatHandler privateChatHandler() {
		return new PrivateChatHandler();
	}
	
	// 웹 소켓 사용시 컨테이너 설정 빈 생성
	@Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
		ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
		container.setMaxTextMessageBufferSize(8192);
		container.setMaxBinaryMessageBufferSize(8192);
		
		return container;	
	}
	
	// cross origin 설정
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://192.168.0.144:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
	
	
} // end ServletConfig




