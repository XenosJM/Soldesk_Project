package com.soldesk.ex01.service;

import com.soldesk.ex01.domain.MemberVO;

public interface UtilService {
	
	// ȸ�����Խ� ���̵� �ߺ� üũ
	MemberVO checkId(String memberId);
	// ���� üũ
	MemberVO checkEmail(String memberEmail);
	
}
