package com.soldesk.ex01.handler;

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
//		log.info(session.getPrincipal().getName() + "���� ����Ǿ����ϴ�.");
		log.info("����Ȯ��");
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage msg) throws IOException {
//		��ť��Ƽ ������ ����� �ڵ�
//		String userName = session.getPrincipal().getName();
//		String newMsg = userName + ":" + msg.getPayload();
//		
//		session.sendMessage(new TextMessage(newMsg));
		session.sendMessage(new TextMessage(msg.getPayload()));
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		log.info("��������");
	}
}





