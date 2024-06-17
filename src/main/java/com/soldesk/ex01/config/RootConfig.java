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
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.soldesk.ex01.util.AuthCodeGenerator;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

// root-context.xml�� ����
@Configuration
@ComponentScan(basePackages = {"com.soldesk.ex01.service"})
@ComponentScan(basePackages = {"com.soldesk.ex01.aspect"})
@EnableAspectJAutoProxy
@MapperScan(basePackages = {"com.soldesk.ex01.persistence"})
@EnableTransactionManagement // Ʈ����� ���� Ȱ��ȭ
public class RootConfig {

	
   @Bean // ������ bean���� ����
   public DataSource dataSource() { // DataSource ��ü ���� �޼���
      HikariConfig config = new HikariConfig(); // ���� ��ü
      config.setDriverClassName("oracle.jdbc.OracleDriver"); // jdbc ����̹� ����
      // ���� ����Ŭ ���� ����
//		config.setJdbcUrl("jdbc:oracle:thin:@192.168.0.161:1521:xe");
//		config.setUsername("sdp");
//		config.setPassword("asdf");	
      // aws ����Ŭ ���� ����
      config.setJdbcUrl("jdbc:oracle:thin:@teamproject.c1asumy42bvk.ap-northeast-2.rds.amazonaws.com:1521:database"); // DB ���� url
      config.setUsername("soldeskTeam"); // DB ����� ���̵�
      config.setPassword("soldeskProject"); // DB ����� ��й�ȣ
      
      config.setMaximumPoolSize(10); // �ִ� Ǯ(Pool) ũ�� ����
      config.setConnectionTimeout(30000); // Connection Ÿ�� �ƿ� ����(30��)
      HikariDataSource ds = new HikariDataSource(config); // config ��ü�� �����Ͽ� DataSource ��ü ����
      return ds; // ds ��ü ����
   }
   
   // �̸��� ����, ���̵� �� ��й�ȣ ����� ���� ��ȣ ������
   @Bean
   public AuthCodeGenerator authCodeGenerator() {
       return new AuthCodeGenerator();
   }
   
   @Bean
   public JavaMailSender mailSender() { // �̸��� Ȯ�� �Ǵ� ���̵� ��й�ȣ ã��� �̿��� ��ü ���� �޼���
	   JavaMailSenderImpl mailSender = new JavaMailSenderImpl(); // ��ü ����
	   mailSender.setHost("smtp.gmail.com"); // �̸��� ���ۿ� ���� smtp ȣ��Ʈ ����
	   mailSender.setPort(587); // ��Ʈ ����
	   mailSender.setUsername("wjdalsqaaz123@gmail.com"); // ���� �̸���
	   mailSender.setPassword("lmob akef narj lhcu"); // ������ �� ��й�ȣ �Է�.
	   
	   Properties javaMailProperties = new Properties(); // JavaMail �Ӽ� ������ ���� ��ü ����
	   javaMailProperties.put("mail.tranport.protocl", "smtp"); // smtp�� �������ݷ� ���
	   javaMailProperties.put("mail.smtp.auth", "true"); // smtp ������ ���� �ʿ�
	   javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL ���� ���丮 Ŭ���� ���
	   javaMailProperties.put("mail.smtp.starttls.enable", "true"); // STARTTLS(TLS�� �����ϴ� ���)�� ����Ͽ� ��ȣȭ�� ����� Ȱ��ȭ
	   javaMailProperties.put("mail.debug", "true"); // ����� ���
	   javaMailProperties.put("mail.smtp.ssl.trust", "smtp.naver.com"); //smtp ������ ssl �������� �ŷ�
	   javaMailProperties.put("mail.smtp.ssl.protocols", "TLSv1.2"); //����� ssl �������� ����
	   
	   mailSender.setJavaMailProperties(javaMailProperties); // �̸����� ���� ��ü�� properties ����	   
	   return mailSender;
   }
   
   @Bean
   public SqlSessionFactory sqlSessionFactory() throws Exception { 
      SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
      sqlSessionFactoryBean.setDataSource(dataSource());
      return (SqlSessionFactory) sqlSessionFactoryBean.getObject();
   }
   
   // Ʈ����� �Ŵ��� ��ü�� ������ ���
   @Bean
   public PlatformTransactionManager transactionManager() {
      return new DataSourceTransactionManager(dataSource());
   }
   
   // Jackson �� java ��¥ ��ü ���� �߰�
   @Bean
   public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
       Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
       builder.modules(new JavaTimeModule());
       return builder;
   }
} // end RootConfig