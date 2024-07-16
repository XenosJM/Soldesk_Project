package com.soldesk.ex01.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.soldesk.ex01.jwt.JwtHandshakeInterceptor;
import com.soldesk.ex01.jwt.JwtChannelInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	@Autowired
	private JwtHandshakeInterceptor handshakeInterceptor;
	
	@Autowired
	private JwtChannelInterceptor channelInterceptor;
	
	
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
	    // 클라이언트가 구독할 메시지 대상(prefix)을 설정.
	    // 설정한 엔드포인트로 시작하는 경로들은 메시지 브로커가 처리함
	    registry.enableSimpleBroker("/topic", "/friend");

	    // 클라이언트에서 메시지를 전송할 목적지(prefix)를 설정합니다.
	    // 클라이언트는 "/app"으로 시작하는 경로를 통해 서버에 메시지를 보낼 수 있습니다.
	    registry.setApplicationDestinationPrefixes("/app");
	    
	    // 특정 사용자에게 메시지 전송할때 사용할 설정
	    registry.setUserDestinationPrefix("/private");
	}

	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
	    // 클라이언트는 "/ws" 엔드포인트를 통해 WebSocket 연결을 시도
	    registry.addEndpoint("/ws/init")
//	    		.setAllowedOrigins("http://192.168.0.144:3000")
	    		.setAllowedOrigins("*")
	    		.addInterceptors(handshakeInterceptor);
//	            브라우저가 WebSocket을 지원하지 않을 때 대체 가능한 방법을 제공할 SockJs 사용
//	    		왜인지는 모르겠으나 withSockJs()를 사용하면 확인이 안됨
//	    		.withSockJS();
	}
	
	@Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(channelInterceptor);
    }
	

}
