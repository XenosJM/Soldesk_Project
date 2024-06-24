package com.soldesk.ex01.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soldesk.ex01.domain.JwtTokenDTO;
import com.soldesk.ex01.service.MemberService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/login")
@Log4j
public class LoginRestController {
	
	@Autowired
	private MemberService member;
	
	@PostMapping("/check")
	public ResponseEntity<String> memberCheck(@RequestBody Map<String, String> map, HttpServletResponse res) {
		log.info("memberCheck()");
		// TODO 로그인 단에서 리절트가 빈값이면 로그인 실패로 처리하도록 할 것.
		JwtTokenDTO token = member.memberCheck(map, res);
		String jwtToken = token.getAccessToken(); 
		// 헤더에 JWT 토큰 추가 예시
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtToken);
		return new ResponseEntity<>(jwtToken, HttpStatus.OK);
	}
}
