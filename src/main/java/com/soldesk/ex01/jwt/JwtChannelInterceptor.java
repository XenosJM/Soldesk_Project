package com.soldesk.ex01.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

//@Component
@Log4j
public class JwtChannelInterceptor{
	
//	@Autowired
//	private JwtTokenProvider tokenProvider;
//	
//	@Override
//    public Message<?> preSend(Message<?> msg, MessageChannel channel) {
//		log.info("인터셉터 시작");
//        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(msg, StompHeaderAccessor.class);
//        // headerAccessor가 null이 아니어야 하도록 강제 하는 코드 null 방지요 코드임
//        // 사실 쓸 이유가 크게 없어보임
//        assert headerAccessor != null;
//        
//        if (headerAccessor.getCommand() == StompCommand.CONNECT) { // 연결 시에한 header 확인
//        	String bearerToken = headerAccessor.getFirstNativeHeader("Authorization");
//            if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
//                log.warn("Authorization 헤더가 없거나 잘못되었습니다.");
//                return null;
//            }
//
//            String accessToken = bearerToken.substring(7).trim();
//            
//            boolean checkAccess = tokenProvider.validateToken(accessToken);
//            
//            if(checkAccess) {
//            	
//            	Authentication auth = tokenProvider.getAuthentication(accessToken);
//            	
//            	// SecurityContext에 인증 객체 설정
//            	SecurityContextHolder.getContext().setAuthentication(auth);
//            	log.info("현재 사용자: {}" + auth.getName());
//            	log.info("사용자의 권한: {}" + auth.getAuthorities());
//            	
//	            headerAccessor.setHeader("Authorization", "Bearer " + accessToken);
//            	
//            	return msg;
//            } else {
//            	// 검증 실패시
//            	headerAccessor.getSessionAttributes().put("Authorization", "Authentication failed");
////            headerAccessor.addNativeHeader("User", String.valueOf(memberId));
//            	return null;
//            }
//        }
//        return msg;
//    }
}
