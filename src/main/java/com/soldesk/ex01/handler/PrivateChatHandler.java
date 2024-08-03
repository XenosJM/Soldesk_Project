package com.soldesk.ex01.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.soldesk.ex01.service.FriendService;

import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class PrivateChatHandler extends TextWebSocketHandler {
	
	@Autowired
	private FriendService friend;
	
	// 로그인 하면 이 핸들러의 엔드포인트로 연결을한다. 연결이 되었을때
	// 로그인한 사용자의 친구목록을 가져오고 로그인시 사용자의 친구목록을
	// 가져오고 온라인인 사용자이고 웹소켓 연결이 되어있는 사용자들에게
	// 알림을 보냄
	
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


