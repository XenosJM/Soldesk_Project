package com.soldesk.ex01.config;

import java.time.Duration;
import java.util.Properties;

import javax.crypto.SecretKey;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.soldesk.ex01.service.UserDetailServiceImple;
import com.soldesk.ex01.util.AuthCodeGenerator;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

// root-context.xml怨� �룞�씪
@Configuration
@ComponentScan(basePackages = {"com.soldesk.ex01.service", "com.soldesk.ex01.aspect", "com.soldesk.ex01.jwt"})
@EnableAspectJAutoProxy
@MapperScan(basePackages = {"com.soldesk.ex01.persistence"})
@EnableTransactionManagement  // 트랜잭션 관리 활성화
public class RootConfig {

	
   @Bean // 스프링 bean으로 설정
   public DataSource dataSource() { // DataSource 객체 리턴 메서드
      HikariConfig config = new HikariConfig(); // 설정 객체 
      config.setDriverClassName("oracle.jdbc.OracleDriver"); // jdbc 드라이버 정보
      
      config.setJdbcUrl("jdbc:oracle:thin:@sdp.c1asumy42bvk.ap-northeast-2.rds.amazonaws.com:1521:DATABASE"); // DB 연결 url
      config.setUsername("admin"); // DB 사용자 아이디
      config.setPassword("soldeskProject!"); // DB 사용자 비밀번호
      
      config.setMaximumPoolSize(10); // 최대 풀(Pool) 크기 설정
      config.setConnectionTimeout(30000); // Connection 타임 아웃 설정(30초)
      HikariDataSource ds = new HikariDataSource(config); // config 객체를 참조하여 DataSource 객체 생성
      return ds;  // ds 객체 리턴
   }
   
   //TODO 異뷀썑 梨꾪똿諛� 湲곕줉 �궡�뿭�쓣 redis �넻�빐�꽌 �븯�룄濡� 諛붽퓭蹂쇨쾬
   
   // 이메일 인증, 아이디 및 비밀번호 변경용 인증 번호 생성기
   @Bean
   public AuthCodeGenerator authCodeGenerator() {
       return new AuthCodeGenerator();
   }
   
   @Bean
   public JavaMailSender mailSender() { // �씠硫붿씪 �솗�씤 �삉�뒗 �븘�씠�뵒 鍮꾨�踰덊샇 李얘린�떆 �씠�슜�븷 媛앹껜 由ы꽩 硫붿꽌�뱶
	   JavaMailSenderImpl mailSender = new JavaMailSenderImpl(); // 媛앹껜 �깮�꽦
	   mailSender.setHost("smtp.gmail.com"); // �씠硫붿씪 �쟾�넚�뿉 �궗�슜�맆 smtp �샇�뒪�듃 �꽕�젙
	   mailSender.setPort(587); // �룷�듃 �꽕�젙
	   mailSender.setUsername("wjdalsqaaz123@gmail.com"); // �궗�슜�맆 �씠硫붿씪
	   mailSender.setPassword("lmob akef narj lhcu"); // �깮�꽦�븳 �빋 鍮꾨�踰덊샇 �엯�젰.
	   
	   Properties javaMailProperties = new Properties(); // JavaMail 속성 설정을 위한 객체 생성
	   javaMailProperties.put("mail.tranport.protocl", "smtp"); // smtp를 프로토콜로 사용
	   javaMailProperties.put("mail.smtp.auth", "true"); // smtp 서버에 인증 필요
	   javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL 소켓 팩토리 클래스 사용
	   javaMailProperties.put("mail.smtp.starttls.enable", "true"); // STARTTLS(TLS를 시작하는 명령)를 사용하여 암호화된 통신을 활성화
	   javaMailProperties.put("mail.debug", "true"); // 디버깅 출력
	   javaMailProperties.put("mail.smtp.ssl.trust", "smtp.naver.com"); //smtp 서버의 ssl 인증서를 신뢰
	   javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2"); //사용할 ssl 프로토콜 버전
	   
	   mailSender.setJavaMailProperties(javaMailProperties); // 이메일을 보낼 객체에 properties 세팅   
	   return mailSender;
   }
   
   @Bean
   public SqlSessionFactory sqlSessionFactory() throws Exception { 
      SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
      sqlSessionFactoryBean.setDataSource(dataSource());
      return (SqlSessionFactory) sqlSessionFactoryBean.getObject();
   }
   
   // 트랜잭션 매니저 객체를 빈으로 등록
   @Bean
   public PlatformTransactionManager transactionManager() {
      return new DataSourceTransactionManager(dataSource());
   }
   
   // Jackson 에 java 날짜 객체 설정 추가
   @Bean
   public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
       Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
       builder.modules(new JavaTimeModule());
       return builder;
   }
   
   @Bean
   public TaskScheduler taskScheduler() {
       return new ConcurrentTaskScheduler(); // 또는 사용할 TaskScheduler 구현체를 반환
   }
   
   // JWT 생성키 비밀번호
   @Bean
   public SecretKey secretKey() {
       String secret = "7IKs7Jqp7J6QIOyduOymneydtCDrkJjslrQg7J6I64qUIO2MgO2UhOuhnOygne2KuCDsoJHqt7wg7Yag7YGw7J6F64uI64ukLg=="; // base64 인코딩된 비밀번호
       byte[] keyByte = Decoders.BASE64.decode(secret); // Base64 臾몄옄�뿴�쓣 �뵒肄붾뵫�븯�뿬 諛붿씠�듃 諛곗뿴濡� 蹂��솚
       return Keys.hmacShaKeyFor(keyByte); // �뵒肄붾뵫�맂 諛붿씠�듃 諛곗뿴�쓣 �궗�슜�븯�뿬 HMAC-SHA �궎 �깮�꽦
   }

   // JWT 액세스 토큰 만료시간
   @Bean
   public Duration accessTokenExpiration() {
       return Duration.ofMinutes(30); // 30분
   }
   

   // JWT 리프레시 토큰 만료시간
   @Bean
   public Duration refreshTokenExpiration() {
       return Duration.ofDays(7); // 1주일
   }
} // end RootConfig