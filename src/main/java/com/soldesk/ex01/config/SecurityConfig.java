package com.soldesk.ex01.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.authentication.AuthenticationManager;
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
import com.soldesk.ex01.service.UserDetailServiceImple;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 메소드 수준 보안 설정을 활성화
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final String Hierarchy = "ROLE_HEAD_MANAGER > ROLE_MANAGER\n" +
				                     "ROLE_MANAGER > ROLE_USER\n" +
				                     "ROLE_USER > ROLE_GUEST";
	
	@Autowired
	private UserDetailServiceImple userDetail;
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		log.info("http 시큐리티 설정");
		// 권한 설정 및 로그인 로그아웃 관련
		httpSecurity
			.authorizeRequests()  // 요청에 권한 부여
				.antMatchers("/", "/member/regist", "/member/findIdPw", "/login/check", "/board/list", "/board/detail", "/board/search", "/util/**","/reply/{boardId}","/rereply/{boardId}","/category/list","/category/detail","/board/recommendlist", "/ws/**" ).permitAll()  // 루트 URL에 대한 모든 사용자 접근
				// TODO 웹소켓 엔드포인트도 추가해야하면 할것
				.antMatchers("/member/**", "/friend/**", "/reply/**", "/rereply/**", "/attach/**", "/board/**","/request/**","/receive/**").hasAnyRole("MEMBER", "MANAGER", "HEAD_MANAGER")  // 루트 URL에 대한 MEMBER 역할을 가진 사용자만 접근 가능
				.antMatchers("/role/**").hasRole("HEAD_MANAGER")
				
				.anyRequest().authenticated() // 이외에 URL은 사용자 인증을 수행해야 함
					.and()
//				.rememberMe()
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
				.disable() // 리액트랑 연결시 적용
			.sessionManagement()
	        	.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 관리 설정 (STATELESS: 세션을 사용하지 않음)
	        		.and()
//				.csrfTokenRepository(cookieCsrfRepository()) // CSRF 토큰을 쿠키로 저장 리액트랑 연결 안할시 사용.
//				.and()
				
			.headers()
				.contentSecurityPolicy("script-src 'self' ")
					.and()
				.xssProtection()
					.block(true)
					.and();
		
		httpSecurity.cors().configurationSource(corsConfigSource());
		// 필터 관련 설정
		httpSecurity
		  	.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)  // JwtAuthenticationFilter 추가
    		.addFilterBefore(multipartFilter(), CsrfFilter.class) // MultipartFilter를 CSRF 필터 전에 적용
    		.addFilterBefore(encodingFilter(), MultipartFilter.class); // CharacterEncodingFilter를 MultipartFilter 전에 적용
//			.addFilterAt(new JwtAuthFilter(authenticationManager(), jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
//			.addFilterBefore(new JwtAuthFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
				
	}
	
//	// 역할 계층 구조를 정의하는 RoleHierarchyImpl 빈 설정
//	@Bean
//	public RoleHierarchyImpl roleHierarchy() {
//		log.info("권한 계층구조 설정");
//		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
//		// 역할 계층 구조 설정: ROLE_ADMIN > ROLE_STAFF > ROLE_USER > ROLE_GUEST
//		roleHierarchy.setHierarchy(Hierarchy);
//        return roleHierarchy;
//    } 
//	// 역할 계층을 평가하는 RoleHierarchyVoter 빈 설정
//    @Bean
//    public RoleHierarchyVoter roleHierarchyVoter() {
//    	log.info("계층 평가 빈");
//    	// RoleHierarchyVoter는 RoleHierarchyImpl을 참조하여 역할 계층을 평가함
//        return new RoleHierarchyVoter(roleHierarchy());
//    }
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		log.info("jwt 필터");
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
		return jwtAuthenticationFilter;
	}
	
	@Bean
	public MultipartFilter multipartFilter() {
		log.info("멀티파트 필터");
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
		log.info("인코딩 필터");
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
        corsConfig.setAllowedOrigins(List.of("http://192.168.0.147:3000"));
//        corsConfig.setAllowedOrigins(List.of("*"));
        // 허용할 HTTP 메서드를 설정
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
        // 허용할 HTTP 헤더를 설정합니다.
        corsConfig.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "Refresh-Token"));
//        corsConfig.setAllowedHeaders(List.of("*"));
        // 자격 증명을 포함한 요청을 허용.
        corsConfig.setAllowCredentials(true);
        // 리액트쪽에서 토큰을 받아 로컬스토리지에 저장 할수 있도록 명시
        // 다만 헤더를 노출시켜 토큰을 사용할수있게 접근하는것은 문제가 있을수 밖에 없을것 같아
        // 찾아보니 HttpOnly 쿠키를 쓰면 된다고 하지만 그럴 경우 리액트에서 가져다 사용할수가 없다.
        // 다른 방법을 찾아본 결과 https 얘기가 나와 찾아보니 인증서를 발급 받는 형태라 발급 받은 인증서를
        // 서버에 어떻게 세팅하는지 세팅법 정도만 배우고 적용은 못함.
        // 실제 서비스 환경에서는 다른 방법을 쓰도록 알아봐야함.
        corsConfig.addExposedHeader("Authorization");
        corsConfig.addExposedHeader("Refresh-Token");
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
		log.info("웹 시큐리티 설정");
		// 정적인 리소스를 체크 안하도록 설정
		web.ignoring().antMatchers("/resources/**", "/css/**", "/js/**", "/images/**");
	}
	
	
	// csrf 비활성화되어 사용되지 않음.
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
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
	
	// 로그아웃 성공시 사용되는 핸들러
	@Bean
	public LogoutSuccessHandler logoutHandler() {
		log.info("로그아웃핸들러");
		SimpleUrlLogoutSuccessHandler handler = new SimpleUrlLogoutSuccessHandler();
		handler.setDefaultTargetUrl("/");
		handler.setAlwaysUseDefaultTargetUrl(true);
		return handler;
	}
	// 로그인 예외사항 발생시 사용될 핸들러
	@Bean
	public AccessDeniedHandler denyHandler() {
		log.info("액세스 거부 핸들러");
		return  new AccessDeniedHandler() {
			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response,
					AccessDeniedException accessDeniedException) throws IOException, ServletException {
//				log.info(accessDeniedException);
				log.info(request.getRequestURI());
				String username = (request.getUserPrincipal() != null) ? request.getUserPrincipal().getName() : "Anonymous";
		        log.error("Access denied for user: {} - Reason: {}", username, accessDeniedException.getMessage());
		        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
//				response.sendRedirect("/ex01/error/403");
			}
	    };
	}
	
	
}
