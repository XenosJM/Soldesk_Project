package com.soldesk.ex01.jwt;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class JwtWebSocketInterceptor implements ChannelInterceptor {
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        assert headerAccessor != null;
        if (headerAccessor.getCommand() == StompCommand.CONNECT) { // 연결 시에한 header 확인
            String bearerToken = String.valueOf(headerAccessor.getNativeHeader("Authorization").get(0));
            String accessToken = bearerToken.split(" ")[1].trim();

            Authentication auth = tokenProvider.getAuthentication(accessToken);

            // SecurityContext에 인증 객체 설정
            SecurityContextHolder.getContext().setAuthentication(auth);
            log.info("현재 사용자: {}" + auth.getName());
            log.info("사용자의 권한: {}" + auth.getAuthorities());
            headerAccessor.setHeader("accessToken", accessToken);

//            headerAccessor.addNativeHeader("User", String.valueOf(memberId));
        }
        return message;
    }
}
