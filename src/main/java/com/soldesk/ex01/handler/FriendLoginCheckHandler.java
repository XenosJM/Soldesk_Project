package com.soldesk.ex01.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

//@Component
public class FriendLoginCheckHandler {
	
	@Autowired
	private SimpMessagingTemplate msgTemp;
	
	
	
	public void onLogin(String memberId) {
		msgTemp.convertAndSend("/topic/friend/login", memberId + "님이 로그인 하셨습니다.");
	}
	
	public void onLogout(String memberId) {
		msgTemp.convertAndSend("/topic/friend/logout", memberId + "님이 로그아웃 하셨습니다.");
	}
}
