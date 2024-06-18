package com.soldesk.ex01.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.soldesk.ex01.service.UserDetailServiceImple;

import lombok.extern.log4j.Log4j;

@Configuration
@EnableWebSecurity
@Log4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailServiceImple userDetail;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		log.info("시큐리티가 실행됨");
		httpSecurity

			.authorizeRequests()  // 요청에 권한 부여
				.antMatchers("/", "/member/regist", "/member/findIdPw", "/member/check", "/board/list", "/board/detail", "/board/search", "/util/**").permitAll()  // 루트 URL에 대한 모든 사용자 접근
				.antMatchers("/member/**", "/friend/**", "/reply/**", "/rereply/**", "/attach/**").permitAll()  // 루트 URL에 대한 모든 사용자 접근
				.antMatchers("/board/**").hasRole("MEMBER")
				.anyRequest().authenticated() // 이외에 URL은 사용자 인증을 수행해야 함
				.and()
				
			.formLogin()
				.loginPage("/login") // 권한 없을때 이동할 로그인 페이지
				.loginProcessingUrl("/member/check")
				.defaultSuccessUrl("/")
				.successHandler(loginHandler()) // 로그인 성공이 이행할 핸들러
				.failureUrl("/login?error=true") // 로그인 실패시 보여질 url 
				.usernameParameter("memberId")
                .passwordParameter("memberPassword")
				.permitAll()
				
				.and()
			.logout()
				.logoutUrl("/login/checkout")
				.logoutSuccessHandler(logoutHandler())
				.permitAll()
				.and()
			.exceptionHandling()
				.accessDeniedHandler(denyHandler())
				.and()
			.csrf()
//				.disable();
			 	.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // CSRF 토큰을 쿠키로 저장
				.and();
				
//		httpSecurity.cors().configurationSource(corsConfigurationSource());
				
	}
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
		// 새로운 CORS 설정을 생성.
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // 오리진 패턴을 설정. 여기서는 특정 IP와 포트를 가진 도메인을 허용.
        corsConfiguration.setAllowedOrigins(List.of("http://192.168.0.144:3000"));
        // 허용할 HTTP 메서드를 설정
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
        // 허용할 HTTP 헤더를 설정합니다.
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        // 자격 증명을 포함한 요청을 허용.
        corsConfiguration.setAllowCredentials(true);

        // 새로운 URL 기반 CORS 설정 소스를 생성.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 모든 경로에 대해 CORS 설정을 적용합니다.
        source.registerCorsConfiguration("/**", corsConfiguration);

        // CORS 설정 소스를 반환합니다.
        return source;
    }
	
	
	// AuthenticationManagerBuilder 객체를 통해 인증기능을 구성
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("권한 확인");
		auth.userDetailsService(userDetail);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// 정적인 리소스를 체크 안하도록 설정
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
	
	// 로그인 성공시 사용될 핸들러
	@Bean
	public AuthenticationSuccessHandler loginHandler() {
		SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
		handler.setUseReferer(true); // 이전 요청으로 돌아가는 리퍼러 설정
		return 	handler;
	}
	// 로그아웃 성공시 사용되는 핸들러
	@Bean
	public LogoutSuccessHandler logoutHandler() {
		SimpleUrlLogoutSuccessHandler handler = new SimpleUrlLogoutSuccessHandler();
		handler.setDefaultTargetUrl("/");
		handler.setAlwaysUseDefaultTargetUrl(true);
		return handler;
	}
	// 로그인 예외사항 발생시 사용될 핸들러
	@Bean
	public AccessDeniedHandler denyHandler() {
		return  new AccessDeniedHandler() {
			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response,
					AccessDeniedException accessDeniedException) throws IOException, ServletException {
				response.sendRedirect("/error/403");
			}
	    };
	}
	
	
}
