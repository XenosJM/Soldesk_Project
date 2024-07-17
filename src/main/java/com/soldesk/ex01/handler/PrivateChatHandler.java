package com.soldesk.ex01.handler;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class PrivateChatHandler extends TextWebSocketHandler {
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		log.info(session.getPrincipal().getName() + "님이 로그인 하셧습니다.");
		log.info("연결 개시");
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage msg) throws IOException {
		String userName = session.getPrincipal().getName();
		String newMsg = userName + ":" + msg.getPayload();
		
		String payload = msg.getPayload();
        log.info("payload {}" + payload);
        TextMessage textMessage = new TextMessage("Welcome chatting sever~^^");
        session.sendMessage(textMessage);
		
		session.sendMessage(new TextMessage(newMsg));
		session.sendMessage(new TextMessage(msg.getPayload()));
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		log.info("연결 종료");
	}
}

/* 
 * 리액트쪽이랑 커뮤니케이션 더 활발하게 할것.
 * 프레임워크는 구조를 강제해두었다.
 * 
 * 
 */



