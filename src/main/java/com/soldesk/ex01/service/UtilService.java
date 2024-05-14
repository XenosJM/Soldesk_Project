package com.soldesk.ex01.service;

import com.soldesk.ex01.domain.MemberVO;

public interface UtilService {
	
	// 회원가입시 아이디 중복 체크
	MemberVO checkId(String memberId);
	// 메일 체크
	MemberVO checkEmail(String memberEmail);
	
}
