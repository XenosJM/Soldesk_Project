package com.soldesk.ex01.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;

import com.soldesk.ex01.jwt.JwtAuthenticationFilter;
import com.soldesk.ex01.jwt.JwtTokenProvider;
import com.soldesk.ex01.service.UserDetailServiceImple;

import lombok.extern.log4j.Log4j;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true) // 메소드 수준 보안 설정을 활성화
@Log4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailServiceImple userDetail;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
//	private final JwtTokenProvider jwtTokenProvider;
//	
//	public  SerurityConfig(JwtTokenProvider jwtTokenProvider) {
//		this.jwtTokenProvider = jwtTokenProvider;
//	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		log.info("시큐리티가 실행됨");
		// 권한 설정 및 로그인 로그아웃 관련
		httpSecurity
			.authorizeRequests()  // 요청에 권한 부여
				.antMatchers("/", "/member/regist", "/member/findIdPw", "/member/check", "/board/list", "/board/detail", "/board/search", "/util/**").permitAll()  // 루트 URL에 대한 모든 사용자 접근
				.antMatchers("/member/**", "/friend/**", "/reply/**", "/rereply/**", "/attach/**", "/board/**").hasRole("MEMBER")  // 루트 URL에 대한 MEMBER 역할을 가진 사용자만 접근 가능
				.anyRequest().authenticated() // 이외에 URL은 사용자 인증을 수행해야 함
					.and()
//			.formLogin()
//				.loginPage("/login") // 권한 없을때 이동할 로그인 페이지
//				.loginProcessingUrl("/member/check")
//				.defaultSuccessUrl("/")
////				.successHandler(loginHandler()) // 로그인 성공이 이행할 핸들러
//				.failureUrl("/login?error=true") // 로그인 실패시 보여질 url 
//				.usernameParameter("memberId")
//                .passwordParameter("memberPassword")
//				.permitAll()
//					.and()
//			.logout()
//				.logoutUrl("/logout")
//				.logoutSuccessUrl("/")
//				.logoutSuccessHandler(logoutHandler())
//				.permitAll()
//					.and()					
			.exceptionHandling()
				.accessDeniedHandler(denyHandler())
					.and();
		// csrf 및 다른 보안 설정 관련
		httpSecurity
			.csrf()
				.disable() // 리액트랑 연결시 적용할지 고민
				
//				.csrfTokenRepository(cookieCsrfRepository()) // CSRF 토큰을 쿠키로 저장
//				.and()
				
			.headers()
				.contentSecurityPolicy("script-src 'self' ")
					.and()
				
				.xssProtection()
					.block(true)
					.and();
		
//		httpSecurity.cors().configurationSource(corsConfigSource());
		// 필터 관련 설정
		httpSecurity
//			.sessionManagement()
//	        	.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 관리 설정 (STATELESS: 세션을 사용하지 않음)
//	        		.and()
		  	.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)  // JwtAuthenticationFilter 추가
    		.addFilterBefore(multipartFilter(), CsrfFilter.class) // MultipartFilter를 CSRF 필터 전에 적용
    		.addFilterBefore(encodingFilter(), MultipartFilter.class); // CharacterEncodingFilter를 MultipartFilter 전에 적용
//			.addFilterAt(new JwtAuthFilter(authenticationManager(), jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
//			.addFilterBefore(new JwtAuthFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
				
	}
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
		return jwtAuthenticationFilter;
	}
	
	@Bean
	public MultipartFilter multipartFilter() {
		MultipartFilter multipartFilter = new MultipartFilter();
		// 필터에 적용시킬 멀티파트 빈 이름, 이 설정이 있어야 멀티파트가 제대로 수행됨 정확히는 CSRF토큰이 인증 가능해져서 accessDeny를 안당함.
        multipartFilter.setMultipartResolverBeanName("multipartResolver");
        return multipartFilter;
	}
	
	// CommonsMultipartResolver bean 설정
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(10485760); // 10MB
        resolver.setMaxUploadSizePerFile(10485760); // 10MB
        return resolver;
    }
		
	@Bean
	public CharacterEncodingFilter encodingFilter() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);
		return encodingFilter;
	}
	// 크로스 오리진 설정
	@Bean
    public CorsConfigurationSource corsConfigSource() {
		log.info("corsConfigSource");
		// 새로운 CORS 설정을 생성.
        CorsConfiguration corsConfig = new CorsConfiguration();

        // 오리진 패턴을 설정. 여기서는 특정 IP와 포트를 가진 도메인을 허용.
        corsConfig.setAllowedOrigins(List.of("http://192.168.0.144:3000"));
        // 허용할 HTTP 메서드를 설정
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
        // 허용할 HTTP 헤더를 설정합니다.
        corsConfig.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        // 자격 증명을 포함한 요청을 허용.
        corsConfig.setAllowCredentials(true);

        // 새로운 URL 기반 CORS 설정 소스를 생성.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 모든 경로에 대해 CORS 설정을 적용합니다.
        source.registerCorsConfiguration("/**", corsConfig);

        return source;
    }
	
	
	// AuthenticationManagerBuilder 객체를 통해 인증기능을 구성
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info("권한 확인 매니저 설정");
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
		log.info("encoder 사용됨");
        return new BCryptPasswordEncoder();
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
				log.info(accessDeniedException);
				log.info(request.getRequestURI());
				response.sendRedirect("/ex01/error/403");
			}
	    };
	}
	
	
}
