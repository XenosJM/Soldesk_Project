package com.soldesk.ex01.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import com.soldesk.ex01.handler.FriendLoginCheckHandler;
import com.soldesk.ex01.handler.PrivateChatHandler;

import lombok.extern.log4j.Log4j;

@Configuration
@EnableWebSocket
@Log4j
public class WebSocketConfig implements WebSocketConfigurer {
	
	@Autowired
	private PrivateChatHandler privateChatHandler;
	
	@Autowired
	private FriendLoginCheckHandler friendLoginCheckHandler;
	
    @Autowired
    private HandshakeInterceptor jwtHandshakeInterceptor; // 추가한 HandshakeInterceptor

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(privateChatHandler, "/private")
        		.addHandler(friendLoginCheckHandler, "/loginAlarm")
                .addInterceptors(jwtHandshakeInterceptor) // HandshakeInterceptor 추가
                .setAllowedOrigins("*") // 필요한 도메인만 허용하도록 수정 가능
                .withSockJS(); // SockJS 지원 추가
    }
	

	@Bean
	public ServletServerContainerFactoryBean createWebSocketContainer() {
		// 메세지의 크기 제한 설정
		ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
		container.setMaxTextMessageBufferSize(8192);
		container.setMaxBinaryMessageBufferSize(8192);
		return container;
	} // end createWebSocketContainer
	
	
	@Bean
	public Map<String, WebSocketSession> loginAlarmMap(){
		// 웹소켓에 연결된 사용자 맵
		// HashMap을 그냥 쓰면 비동기 상황에서 문제
		return new ConcurrentHashMap<>();
	} // end loginAlarmMap
	
		
}

	



