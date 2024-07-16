package com.soldesk.ex01.jwt;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class JwtHandshakeInterceptor implements HandshakeInterceptor {
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		String bearerToken = request.getHeaders().getFirst("Authorization");
        String accessToken = bearerToken.split(" ")[1].trim();
        String refreshToken = request.getHeaders().getFirst("Refresh-Token");
        
        boolean checkAccess = tokenProvider.validateToken(accessToken);
        boolean checkRefresh = tokenProvider.validateToken(refreshToken);
        
        if(checkAccess) {
        	
        	Authentication auth = tokenProvider.getAuthentication(accessToken);
        	
        	// SecurityContext에 인증 객체 설정
        	SecurityContextHolder.getContext().setAuthentication(auth);
        	log.info("현재 사용자: {}" + auth.getName());
        	log.info("사용자의 권한: {}" + auth.getAuthorities());
        	
            attributes.put("Authorization", "Bearer " + accessToken);
        	
        	return true;
        } else if(checkRefresh) {
        	
        	String memberId = tokenProvider.getUsernameFromToken(refreshToken);
        	
        	accessToken = tokenProvider.generateAccessTokenFromRefreshToken(memberId, refreshToken);
        	Authentication auth = tokenProvider.getAuthentication(accessToken);
        	SecurityContextHolder.getContext().setAuthentication(auth);
        	
        	attributes.put("Authorization", "Bearer " + accessToken);
        	
        }
    	 // 인증 실패 시 연결 거부
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
		return false;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		// TODO 나중에 필요해지면 추가하면됨
		
	}

}
