package com.soldesk.ex01.config;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.soldesk.ex01.persistence.MemberMapper;

import lombok.extern.log4j.Log4j;

@Configuration
@EnableWebSecurity
@Log4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		log.info("��ť��Ƽ�� �����");
		httpSecurity

			.authorizeRequests() 
				.antMatchers("/", "/member/regist", "/member/findIdPw", "/member/check", "/board/list", "/board/detail", "/board/search", "/util/**").permitAll() 
				.antMatchers("/member/**", "/friend/**", "/reply/**", "/rereply/**", "/attach/**", "/board/**").permitAll()
				.anyRequest().authenticated() 
				.and()
				
			.formLogin()
				.loginPage("member/login")
				.loginProcessingUrl("member/login")
				.usernameParameter("memberId")
                .passwordParameter("memberPassword")
				.permitAll()
				
				.and()
			.logout()
				.logoutUrl("/login/checkout")
				.logoutSuccessUrl("/")
				.permitAll()
				
				.and()
			.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				
				.and();
		httpSecurity.cors().configurationSource(corsConfigurationSource());
				
	}
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // ���ο� CORS ������ ����.
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(List.of("http://192.168.0.144:3000"));
        // ������ ������ ����. ���⼭�� Ư�� IP�� ��Ʈ�� ���� �������� ���.
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
        // ����� HTTP �޼��带 ����.
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        // ����� HTTP ����� �����մϴ�.
        corsConfiguration.setAllowCredentials(true);
        // �ڰ� ������ ������ ��û�� ���.

        // ���ο� URL ��� CORS ���� �ҽ��� ����.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        // ��� ��ο� ���� CORS ������ �����մϴ�.

        return source;
        // CORS ���� �ҽ��� ��ȯ�մϴ�.
    }
	
	// AuthenticationManagerBuilder
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("���� Ȯ��");
		 auth.jdbcAuthentication()
         .dataSource(dataSource)
         .usersByUsernameQuery("SELECT MEMBER_ID, MEMBER_PASSWORD, 1 as enabled FROM MEMBER WHERE MEMBER_ID = ?")
		 .authoritiesByUsernameQuery("SELECT m.MEMBER_ID, r.ROLE_NAME " +
                 "FROM MEMBER m " +
                 "JOIN ROLE r ON m.ROLE_ID = r.ROLE_ID " +
                 "WHERE m.MEMBER_ID = ?");
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// ���� ���ҽ��� ����
		web.ignoring().antMatchers("/resources/**", "/css/**", "/js/**", "/images/**");
	}
	
	
	@Bean
    CookieCsrfTokenRepository cookieCsrfRepository() {
        CookieCsrfTokenRepository csrfRepository = new CookieCsrfTokenRepository();

        csrfRepository.setCookieHttpOnly(false);
        csrfRepository.setHeaderName("X-CSRF-TOKEN");
        csrfRepository.setParameterName("_csrf");
        csrfRepository.setCookieName("XSRF-TOKEN");
        //csrfRepository.setCookiePath("..."); // 占썩본占쏙옙: request.getContextPath()

        return csrfRepository;
    }
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
