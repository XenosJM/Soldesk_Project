package com.soldesk.ex01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.soldesk.ex01.domain.ChatGroupVO;
import com.soldesk.ex01.domain.ChatVO;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class WebSocketController {
    
	
	
    // 친구 상태 변화 감지 소켓 연결용 메서드
    @MessageMapping("/sendState")
    @SendTo("/friend/sendState")
    public String friendState(@Payload String msg) {
        return msg;
    }
    
    // TODO 개인 메시지 또는 채팅방 메서드 만들어야함
    
    // 채팅 메시지 전송 메서드
    @MessageMapping("/private/{memberId}")
    @SendTo("/topic/private/{memberId}")
    public ChatVO send(@PathVariable String  memberId, ChatVO message) {
        return message;
    }
}
