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
            String jwt = getJwtFromRequest(request);

            // 추출된 JWT가 유효하면
            if (jwt != null && tokenProvider.validateToken(jwt)) {
                // JWT에서 사용자 이름을 추출
                String username = tokenProvider.getUsernameFromToken(jwt);

                // 추출된 사용자 이름으로 Authentication 객체 생성
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, null);

                // SecurityContext에 인증 객체 설정
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            // 예외 발생 시 로깅
            log.error("Could not set user authentication in security context", e);
        }

        // 다음 필터로 요청과 응답 전달
        filterChain.doFilter(request, response);
    }

    // HTTP 요청에서 Authorization 헤더에서 JWT를 추출하는 메서드
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 이후의 JWT 문자열 반환
        }
        return null; // JWT가 없으면 null 반환
    }
}
