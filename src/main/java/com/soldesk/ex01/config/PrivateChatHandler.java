package com.soldesk.ex01.config;

import java.io.IOException;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.log4j.Log4j;

@Log4j
public class PrivateChatHandler extends TextWebSocketHandler {
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
//		log.info(session.getPrincipal().getName() + "님이 연결되었습니다.");
		log.info("연결확인");
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage msg) throws IOException {
//		시큐리티 적용후 사용할 코드
//		String userName = session.getPrincipal().getName();
//		String newMsg = userName + ":" + msg.getPayload();
//		
//		session.sendMessage(new TextMessage(newMsg));
		session.sendMessage(new TextMessage(msg.getPayload()));
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		log.info("연결종료");
	}
}





