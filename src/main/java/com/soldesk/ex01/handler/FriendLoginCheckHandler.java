package com.soldesk.ex01.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.soldesk.ex01.domain.FriendVO;
import com.soldesk.ex01.service.FriendService;

import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
@Log4j
public class FriendLoginCheckHandler extends TextWebSocketHandler {

	@Autowired
    private Map<String, WebSocketSession> loginAlarmMap;
    
    @Autowired
    private FriendService friend;
    

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	String memberId = session.getPrincipal().getName();
    	loginAlarmMap.put(memberId, session);
    	
    	String loginMember = memberId + " 님이 로그인 하셧습니다.";
		TextMessage msg = new TextMessage(loginMember);
		castState(memberId, msg);
		log.info("연결 개시");
        // 클라이언트 연결 시 세션을 맵에 추가
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String memberId = session.getPrincipal().getName();
        
//        String logoutMember = memberId + "님이 로그아웃 하셨습니다.";
//        TextMessage msg = new TextMessage(logoutMember);
//        castState(memberId, msg);
        
        // 사용자 연결 종료 시 세션을 맵에서 제거
        loginAlarmMap.remove(memberId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received message: " + payload);
    }

    
    public void castState(String memberId, TextMessage msg) {
    	// 로그인한 사용자의 친구목록 가져옴
		List<FriendVO> list = friend.friendList(memberId);
		// 친구알림을 위한 웹소켓에 연결되어있는 사용자들의 이름을 가져옴
		Iterator<String> iterator = loginAlarmMap.keySet().iterator();
		// 값이 존재하는동안
		while (iterator.hasNext()) {
			String sessionMember = iterator.next();
			WebSocketSession loginFriend = loginAlarmMap.get(sessionMember);
			if (loginFriend != null && loginFriend.isOpen()) {
				for(FriendVO friend : list) {
					String friendMemberId = friend.getFriendMemberId();
					// 세션에 접속되있는 회원과 로그인한 사용자의 친구아이디가 일치할때
					if(sessionMember.equals(friendMemberId)) {
						try {
							// 메시지 보내기
							loginFriend.sendMessage(msg);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} // end for문
			} // end 널 체크
		} // end while문
	} // end castOnlinFriend
    
} // end FriendLoginCheckHandler



