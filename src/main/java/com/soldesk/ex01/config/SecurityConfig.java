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
	
	
	// HttpSecurity 객체를 통해 Http 보안 기능을 구성
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		log.info("시큐리티가 실행됨");
		httpSecurity
			.authorizeRequests() // 요청에 권한 부여
				.antMatchers("/", "/member/regist", "/member/findIdPw", "/member/check", "/board/list", "/board/detail", "/board/search", "/util/**").permitAll() // 루트 URL에 대한 모든 사용자 접근을 허용
				.antMatchers("/member/**", "/friend/**", "/reply/**", "/rereply/**", "/attach/**").permitAll()
				.anyRequest().authenticated() // 이외에 URL은 사용자 인증을 수행해야 함
				
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
        // 새로운 CORS 설정을 생성.
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(List.of("http://192.168.0.144:3000"));
        // 오리진 패턴을 설정. 여기서는 특정 IP와 포트를 가진 도메인을 허용.
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
        // 허용할 HTTP 메서드를 설정.
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        // 허용할 HTTP 헤더를 설정합니다.
        corsConfiguration.setAllowCredentials(true);
        // 자격 증명을 포함한 요청을 허용.

        // 새로운 URL 기반 CORS 설정 소스를 생성.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        // 모든 경로에 대해 CORS 설정을 적용합니다.

        return source;
        // CORS 설정 소스를 반환합니다.
    }
	
	// AuthenticationManagerBuilder 객체를 통해 인증기능을 구성
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("권한 확인");
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
		// 정적 리소스들 제외
		web.ignoring().antMatchers("/resources/**", "/css/**", "/js/**", "/images/**");
	}
	
	
	@Bean
    CookieCsrfTokenRepository cookieCsrfRepository() {
        CookieCsrfTokenRepository csrfRepository = new CookieCsrfTokenRepository();

        csrfRepository.setCookieHttpOnly(false);
        csrfRepository.setHeaderName("X-CSRF-TOKEN");
        csrfRepository.setParameterName("_csrf");
        csrfRepository.setCookieName("XSRF-TOKEN");
        //csrfRepository.setCookiePath("..."); // 기본값: request.getContextPath()

        return csrfRepository;
    }
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
