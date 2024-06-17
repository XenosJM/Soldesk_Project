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
	

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.authorizeRequests() 
				.antMatchers("/", "/member/regist", "/member/findIdPw", "/member/check", "/board/list", "/board/detail", "/board/search", "/util/**").permitAll() 
				.antMatchers("/member/**", "/friend/**", "/reply/**", "/rereply/**", "/attach/**","/board/**").permitAll()
				.anyRequest().authenticated() 
				.and()
			.formLogin()
				.loginPage("/login/check")
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
			
	}
	
	// AuthenticationManagerBuilder 占쏙옙체占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙占� 占쏙옙占쏙옙
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
        //csrfRepository.setCookiePath("..."); // 占썩본占쏙옙: request.getContextPath()

        return csrfRepository;
    }
}
