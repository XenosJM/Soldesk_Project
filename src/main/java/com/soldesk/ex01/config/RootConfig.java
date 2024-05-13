package com.soldesk.ex01.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

// root-context.xml과 동일
@Configuration
@ComponentScan(basePackages = {"com.soldesk.ex01.service"})
@ComponentScan(basePackages = {"com.soldesk.ex01.aspect"})
@EnableAspectJAutoProxy
@MapperScan(basePackages = {"com.soldesk.ex01.persistence"})
@EnableTransactionManagement // 트랜잭션 관리 활성화
public class RootConfig {
   
   @Bean // 스프링 bean으로 설정
   public DataSource dataSource() { // DataSource 객체 리턴 메서드
      HikariConfig config = new HikariConfig(); // 설정 객체
      config.setDriverClassName("oracle.jdbc.OracleDriver"); // jdbc 드라이버 정보
      // TODO 집에서 할때는 url 설정 바꿔야함.
//    config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe"); // DB 연결 url
      config.setJdbcUrl("jdbc:oracle:thin:@192.168.0.161:1521:xe"); // DB 연결 url
      config.setUsername("sdp"); // DB 사용자 아이디
      config.setPassword("asdf"); // DB 사용자 비밀번호
      
      config.setMaximumPoolSize(10); // 최대 풀(Pool) 크기 설정
      config.setConnectionTimeout(30000); // Connection 타임 아웃 설정(30초)
      HikariDataSource ds = new HikariDataSource(config); // config 객체를 참조하여 DataSource 객체 생성
      return ds; // ds 객체 리턴
   }
   
   @Bean
   public JavaMailSender mailSender() { // 이메일 확인 또는 아이디 비밀번호 찾기시 이용할 객체 리턴 메서드
	   JavaMailSenderImpl mailSender = new JavaMailSenderImpl(); // 객체 생성
	   mailSender.setHost("smtp.gmail.com"); // 이메일 전송에 사용될 smtp 호스트 설정
	   mailSender.setPort(587); // 포트 설정
	   mailSender.setUsername("wjdalsqaaz123@gmail.com"); // 사용될 이메일
	   mailSender.setPassword("lmob akef narj lhcu"); // 생성한 앱 비밀번호 입력.
	   
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
} // end RootConfig