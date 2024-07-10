package com.soldesk.ex01.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class WebSocketController {
	
	// 친구 상태 변화 감지 소켓 연결용 메서드
	// 로그인 성공시 로그인 되었다는 정보(예시 : "로그인" 또는 "login" 등)를 
	// 로그인하며 연결된 소켓인 이 경로로 보내기
	@MessageMapping("/sendState")
	@SendTo("/topic/friendState")
	public String friendState(String memberState) {
		String msg = "msg";
		return msg;
	}
	
}





