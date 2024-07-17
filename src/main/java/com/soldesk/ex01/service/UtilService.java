package com.soldesk.ex01.service;

import com.soldesk.ex01.domain.MemberVO;

public interface UtilService {
	
	// ȸ�����Խ� ���̵� �ߺ� üũ
	String checkId(String memberId);
	// ���� üũ
	String checkEmail(String memberEmail);
	int updatePassword(MemberVO memberVO);
	
}
