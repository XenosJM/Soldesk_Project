package com.soldesk.ex01.jwt;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class Test implements HandshakeInterceptor {
   
   @Autowired
   private JwtTokenProvider tokenProvider;
   
   @Override
   public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
         Map<String, Object> attributes) throws Exception {
      String bearerToken = request.getHeaders().getFirst("Authorization");
      if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
         log.warn("Authorization 헤더가 없거나 잘못되었습니다.");
         response.setStatusCode(HttpStatus.UNAUTHORIZED);
         return false;
      }

      String accessToken = bearerToken.substring(7).trim();

      try {
         if (tokenProvider.validateToken(accessToken)) {
            Authentication auth = tokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
            attributes.put("Authorization", "Bearer " + accessToken);
            return true;
         } else {
            log.warn("토큰 검증 실패");
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
         }
      } catch (Exception e) {
         log.error("토큰 처리 중 예외 발생", e);
         response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
         return false;
      }
   }

   @Override
   public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
         Exception exception) {
      // 연결 후에 SecurityContext를 지우는 것이 필요할 때 사용합니다.
      SecurityContextHolder.clearContext();
   }
}
