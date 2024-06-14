package com.soldesk.ex01.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soldesk.ex01.domain.MemberVO;
import com.soldesk.ex01.service.MemberService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value="/login")
@Log4j
public class LoginController {
	
	@Autowired
	private MemberService member;
	
	@RequestMapping("/check")
	public ResponseEntity<Integer> login(@RequestBody Map<String, String> res){
		log.info("login");
		int result = 0;
		
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}
}
