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
@RequestMapping(value="/check")
@Log4j
public class CheckIdRESTController {
	
	@Autowired
	MemberService memberService;
	
	@GetMapping("/{memberId}")
	public ResponseEntity<MemberVO> checkId(@PathVariable("memberId")String memberId){
		log.info("checkId()");
		MemberVO memberVO = new MemberVO();
		memberVO = memberService.checkId(memberId);				
		return new ResponseEntity<MemberVO>(memberVO,HttpStatus.OK);
		
	}
}
