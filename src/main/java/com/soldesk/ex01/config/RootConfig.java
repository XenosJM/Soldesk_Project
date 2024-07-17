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
@EnableTransactionManagement // �듃�옖�옲�뀡 愿�由� �솢�꽦�솕
public class RootConfig {

	
   @Bean // �뒪�봽留� bean�쑝濡� �꽕�젙
   public DataSource dataSource() { // DataSource 媛앹껜 由ы꽩 硫붿꽌�뱶
      HikariConfig config = new HikariConfig(); // �꽕�젙 媛앹껜
      config.setDriverClassName("oracle.jdbc.OracleDriver"); // jdbc �뱶�씪�씠踰� �젙蹂�
      // 湲곗〈 �삤�씪�겢 �뿰寃� �꽕�젙
//		config.setJdbcUrl("jdbc:oracle:thin:@192.168.0.161:1521:xe");
//		config.setUsername("sdp");
//		config.setPassword("asdf");	
      // aws �삤�씪�겢 �뿰寃� �꽕�젙
      config.setJdbcUrl("jdbc:oracle:thin:@sdp.c1asumy42bvk.ap-northeast-2.rds.amazonaws.com:1521:DATABASE"); // DB �뿰寃� url
      config.setUsername("admin"); // DB �궗�슜�옄 �븘�씠�뵒
      config.setPassword("soldeskProject!"); // DB �궗�슜�옄 鍮꾨�踰덊샇
      
      config.setMaximumPoolSize(10); // 理쒕� ��(Pool) �겕湲� �꽕�젙
      config.setConnectionTimeout(30000); // Connection ���엫 �븘�썐 �꽕�젙(30珥�)
      HikariDataSource ds = new HikariDataSource(config); // config 媛앹껜瑜� 李몄“�븯�뿬 DataSource 媛앹껜 �깮�꽦
      return ds; // ds 媛앹껜 由ы꽩
   }
   
   //TODO 異뷀썑 梨꾪똿諛� 湲곕줉 �궡�뿭�쓣 redis �넻�빐�꽌 �븯�룄濡� 諛붽퓭蹂쇨쾬
   
   // �씠硫붿씪 �씤利�, �븘�씠�뵒 諛� 鍮꾨�踰덊샇 蹂�寃쎌슜 �씤利� 踰덊샇 �깮�꽦湲�
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
	   
	   Properties javaMailProperties = new Properties(); // JavaMail �냽�꽦 �꽕�젙�쓣 �쐞�븳 媛앹껜 �깮�꽦
	   javaMailProperties.put("mail.tranport.protocl", "smtp"); // smtp瑜� �봽濡쒗넗肄쒕줈 �궗�슜
	   javaMailProperties.put("mail.smtp.auth", "true"); // smtp �꽌踰꾩뿉 �씤利� �븘�슂
	   javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL �냼耳� �뙥�넗由� �겢�옒�뒪 �궗�슜
	   javaMailProperties.put("mail.smtp.starttls.enable", "true"); // STARTTLS(TLS瑜� �떆�옉�븯�뒗 紐낅졊)瑜� �궗�슜�븯�뿬 �븫�샇�솕�맂 �넻�떊�쓣 �솢�꽦�솕
	   javaMailProperties.put("mail.debug", "true"); // �뵒踰꾧퉭 異쒕젰
	   javaMailProperties.put("mail.smtp.ssl.trust", "smtp.naver.com"); //smtp �꽌踰꾩쓽 ssl �씤利앹꽌瑜� �떊猶�
	   javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2"); //�궗�슜�븷 ssl �봽濡쒗넗肄� 踰꾩쟾
	   
	   mailSender.setJavaMailProperties(javaMailProperties); // �씠硫붿씪�쓣 蹂대궪 媛앹껜�뿉 properties �꽭�똿	   
	   return mailSender;
   }
   
   @Bean
   public SqlSessionFactory sqlSessionFactory() throws Exception { 
      SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
      sqlSessionFactoryBean.setDataSource(dataSource());
      return (SqlSessionFactory) sqlSessionFactoryBean.getObject();
   }
   
   // �듃�옖�옲�뀡 留ㅻ땲�� 媛앹껜瑜� 鍮덉쑝濡� �벑濡�
   @Bean
   public PlatformTransactionManager transactionManager() {
      return new DataSourceTransactionManager(dataSource());
   }
   
   // Jackson �뿉 java �궇吏� 媛앹껜 �꽕�젙 異붽�
   @Bean
   public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
       Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
       builder.modules(new JavaTimeModule());
       return builder;
   }
   
   // JWT �떆�겕由욱궎 �꽕�젙
   @Bean
   public SecretKey secretKey() {
       String secret = "7IKs7Jqp7J6QIOyduOymneydtCDrkJjslrQg7J6I64qUIO2MgO2UhOuhnOygne2KuCDsoJHqt7wg7Yag7YGw7J6F64uI64ukLg=="; // base64 �씤肄붾뵫�맂 臾몄옄�뿴
       byte[] keyByte = Decoders.BASE64.decode(secret); // Base64 臾몄옄�뿴�쓣 �뵒肄붾뵫�븯�뿬 諛붿씠�듃 諛곗뿴濡� 蹂��솚
       return Keys.hmacShaKeyFor(keyByte); // �뵒肄붾뵫�맂 諛붿씠�듃 諛곗뿴�쓣 �궗�슜�븯�뿬 HMAC-SHA �궎 �깮�꽦
   }

   // JWT �븸�꽭�뒪 �넗�겙 留뚮즺 湲곌컙 �꽕�젙
   @Bean
   public Duration accessTokenExpiration() {
       return Duration.ofMinutes(30); // 30遺�
   }
   

   // JWT 由ы봽�젅�떆 �넗�겙 留뚮즺 湲곌컙 �꽕�젙
   @Bean
   public Duration refreshTokenExpiration() {
       return Duration.ofDays(7); // 1二쇱씪
   }
} // end RootConfig