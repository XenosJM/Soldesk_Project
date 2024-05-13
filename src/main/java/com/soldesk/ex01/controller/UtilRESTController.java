package com.soldesk.ex01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soldesk.ex01.domain.MemberVO;
import com.soldesk.ex01.service.MemberService;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping(value="/util")
@Log4j
public class UtilRESTController {
	
	@Autowired
	MemberService memberService;
	
	@GetMapping("/checkId/{memberId}")
	public ResponseEntity<Integer> checkId(@PathVariable("memberId")String memberId){
		log.info("checkId()");
		log.info(memberId);
		MemberVO memberVO = memberService.checkId(memberId);
		log.info(memberVO);
		int result = (memberVO != null) ? 1 : 0;
		return new ResponseEntity<Integer>(result,HttpStatus.OK);
		
	}
	
	@GetMapping("/checkEmail/{memberEmail}")
	public ResponseEntity<MemberVO> checkEmail(@PathVariable("memberEmail")String memberEmail) {
		log.info("checkEmail()");
		log.info("memberEmail : " + memberEmail);
		MemberVO memberVO = memberService.checkEmail(memberEmail);
		log.info(memberVO);
		int result = (memberVO != null) ? 1 : 0;
		log.info(result);
		return new ResponseEntity<MemberVO>(memberVO, HttpStatus.OK);
	}
}
