package com.soldesk.ex01.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import com.soldesk.ex01.domain.FriendVO;
import com.soldesk.ex01.service.FriendService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class WebSocketController {

    @Autowired
    private FriendService friendService;
    
//    @Autowired
//	private SimpMessagingTemplate msgTemp;

//    // 친구 상태 변화 감지 소켓 연결용 메서드
//    @MessageMapping("/sendState")
//    @SendTo("/friend/sendState")
//    public String friendState(@Payload String msg) {
//        return msg;
//    }

    // 채팅 메시지 전송 메서드
    @MessageMapping("/private/{memberId}/{friendMemberid}")
    @SendTo("/topic/private/{memberId}")
    public String privateChat(@Payload String msg, @PathVariable String memberId) {
        return msg;
    }
    
//    // 그룹 채팅 전송 메서드
//    @MessageMapping("/group/{groupId}/{memberId}")
//    @SendTo("/topic/group/{groupId}")
//    public String groupChat(@Payload String msg, @PathVariable int groupId) {
//    	return msg;
//    }

    // 로그인 상태를 알릴 친구들에게 메시지 전송 
//    특정 주제에 대해 서버로부터 메시지를 수신받기위해 클라이언트쪽에서 /friend 주제로 구독을 신청 
//    구독이 성공적으로 이루어지면 바로 클라이언트에서  /app/loginAlarm 엔드포인트로 요청
//    loginAlarm가 요청이 되어 실행되면 해당 메서드는 로그인한 유저의 친구들에게 로그인 상태를 알림. /friend 주제로 구독한 클라이언트들에게 메시지를 전송
//    @MessageMapping("/loginAlarm")
//    public void loginAlarm(@Payload String memberId) {
//        // 친구목록 조회
//        List<FriendVO> friendList = friendService.friendList(memberId);
//        
//        // 친구들에게 로그인 상태 메시지 전송
//        for (FriendVO friend : friendList) {
//            String friendMemberId = friend.getFriendMemberId();
//            String msg = memberId + "님이 로그인 하셨습니다.";
//            // 특정 사용자에게 메시지 전송 (/friend/sendState/{friendMemberId})
////            this.sendToUser(friendMemberId, "/friend/sendState", message);
//            msgTemp.convertAndSendToUser(friendMemberId, "/friend/sendState", msg);
//        }
//    }
}
