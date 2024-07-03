package com.soldesk.ex01.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompBrokerRelayMessageHandler;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.soldesk.ex01.handler.PrivateChatHandler;

//@Configuration
//@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer implements WebSocketConfigurer {
	
	private PrivateChatHandler privateChat;
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		
	}

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(privateChat, "/private") // ���� ȣ��
		.setAllowedOrigins("*"); // �㰡�� �����ο����� ���Ḹ ��� ����� �׽�Ʈ�� ��� ���
	}
}
