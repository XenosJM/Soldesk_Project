package com.soldesk.ex01.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.log4j.Log4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    // HTTP 요청을 필터링하여 JWT를 인증하는 메서드
    @Override	
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // HTTP 요청에서 JWT 추출
        	log.info("jwt 토큰들 확인시작");
        	
        	// 액세스 토큰 가져오기
        	String accessToken = getAccessTokenFromRequest(request);
        	// 리프레시 토큰 가져오기
        	String refreshToken = getRefreshTokenFromRequest(request);
        	
            // 액세스 토큰이 존재하며 검증 통과시 true
            Boolean accessCheck = (accessToken != null && tokenProvider.validateToken(accessToken));
            log.info(accessCheck); 
            // 리프레시 토큰이 존재하며 검증에 통과시 true
            Boolean refreshCheck = (refreshToken != null && tokenProvider.validateToken(refreshToken));
            log.info(refreshCheck); 
            // 추출된 JWT가 유효하면
            if (accessCheck) {
            	log.info("액세스토큰 검증 통과");

                // jwt를 이용해 Authentication 객체 생성
                Authentication auth = tokenProvider.getAuthentication(accessToken);

                // SecurityContext에 인증 객체 설정
                SecurityContextHolder.getContext().setAuthentication(auth);
                log.info("현재 사용자: {}" + auth.getName());
                log.info("사용자의 권한: {}" + auth.getAuthorities());
            } else if(refreshCheck) {
            	log.info("액세스토큰 검증 실패, 리프레시 토큰 검증 성공");
            	// 이부분 변경 해야함 
            	String memberId = tokenProvider.getUsernameFromToken(refreshToken);
            	
            	accessToken = tokenProvider.generateAccessTokenFromRefreshToken(memberId, refreshToken);
            	log.info(accessToken);
            }
            log.info("jwt 확인 종료");
        } catch (Exception e) {
            // 예외 발생 시 로깅
            log.error("Could not set user authentication in security context", e);
        }

        // 다음 필터로 요청과 응답 전달
        filterChain.doFilter(request, response);
    }

    private String getRefreshTokenFromRequest(HttpServletRequest request) {
    	log.info("리프레시 토큰 체크중");
    	// 액세스 토큰을 검증시도, 실패시 리프레시 토큰 검증 시도, 실패시 재 로그인
    	// ㄴ 리프레시 토큰 검증 시도 성공시 액세스토큰 재발급
    	String refreshToken = request.getHeader("Refresh-Token");
		return refreshToken;
	}

	// HTTP 요청에서 Authorization 헤더에서 JWT를 추출하는 메서드
    private String getAccessTokenFromRequest(HttpServletRequest request) {
    	log.info("토큰 체크중");
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
        	log.info("토큰 확인 완료");
            return bearerToken.split(" ")[1].trim(); // "Bearer " 이후의 JWT 문자열 반환
        }
        log.info("토큰 없음");
        return null; // JWT가 없으면 null 반환
    }
}
