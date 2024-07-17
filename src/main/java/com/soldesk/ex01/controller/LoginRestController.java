package com.soldesk.ex01.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soldesk.ex01.domain.FriendVO;
import com.soldesk.ex01.service.FriendService;
import com.soldesk.ex01.service.LoginService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/login")
@Log4j
public class LoginRestController {
	
	@Autowired
	private LoginService login;
	
	@Autowired
	private FriendService friend;
	
//	@Autowired
//	private SimpMessagingTemplate msgTemp;
	
	@PostMapping("/check")
	public ResponseEntity<String> memberCheck(@RequestBody Map<String, String> map, HttpServletResponse res) {
		log.info("memberCheck()");
		HttpHeaders header = new HttpHeaders();
		String result = login.memberCheckin(map, res);
		log.info(result);
		
		if(!result.contains("fail")) {
			String accessToken = res.getHeader("Authorization");
			// 헤더에 JWT 토큰 추가 예시
			header.add("Authorization", "Bearer " + accessToken);
			if(res.getHeader("Refresh-Token") != null) {
				String refreshToken = res.getHeader("Refresh-Token");
				header.add("Refresh-Token", refreshToken);
			}
			
//		// 멤버 아이디 선언
//		String memberId = map.get("memberId");
//		// 로그인 상태를 알릴 친구목록
//		List<FriendVO> friendList = friend.friendList(memberId);
//		// 친구목록에 존재하는 친구들에게 메시지 전송
//		for(FriendVO friendVO : friendList) {
//			msgTemp.convertAndSendToUser(friendVO.getFriendMemberId(), "/friend/sendState", memberId + "님이 로그인 하셨습니다.");
//		}
			
		} else {
			
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/checkout")
	public ResponseEntity<String> checkout(String memberId) {
		log.info("checkout");
//		List<FriendVO> friendList = friend.friendList(memberId);
		
//		for(FriendVO friendVO : friendList) {
//			msgTemp.convertAndSendToUser(friendVO.getFriendMemberId(), "/friend/sendState", memberId + "님이 로그아웃 하셨습니다.");
//		}
		return new ResponseEntity<>("success", HttpStatus.OK);
	}

}
