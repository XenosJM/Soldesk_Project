package com.soldesk.ex01.service;

public interface UtilService {
	
	// 회원가입시 아이디 중복 체크
	String checkId(String memberId);
	// 메일 체크
	String checkEmail(String memberEmail);
	
}
