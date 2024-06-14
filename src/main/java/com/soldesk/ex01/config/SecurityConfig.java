package com.soldesk.ex01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	// HttpSecurity ��ü�� ���� Http ���� ����� ����
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.authorizeRequests() // ��û�� ���� �ο�
				.antMatchers("/", "/member/regist", "/member/findIdPw", "/member/check", "/board/list", "/board/detail", "/board/search", "/util/**").permitAll() // ��Ʈ URL�� ���� ��� ����� ������ ���
				.antMatchers("/member/**", "/friend/**", "/board/**", "/reply/**", "/rereply/**", "/attach/**").permitAll()
				.anyRequest().authenticated() // �̿ܿ� URL�� ����� ������ �����ؾ� ��
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.permitAll()
				.and()
			.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.and();
			
	}
	
	// AuthenticationManagerBuilder ��ü�� ���� ��������� ����
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
	}
	
	
	@Bean
    CookieCsrfTokenRepository cookieCsrfRepository() {
        CookieCsrfTokenRepository csrfRepository = new CookieCsrfTokenRepository();

        csrfRepository.setCookieHttpOnly(false);
        csrfRepository.setHeaderName("X-CSRF-TOKEN");
        csrfRepository.setParameterName("_csrf");
        csrfRepository.setCookieName("XSRF-TOKEN");
        //csrfRepository.setCookiePath("..."); // �⺻��: request.getContextPath()

        return csrfRepository;
    }
}
