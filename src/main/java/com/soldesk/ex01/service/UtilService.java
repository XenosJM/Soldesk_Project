package com.soldesk.ex01.service;

import com.soldesk.ex01.domain.MemberVO;

public interface UtilService {
	
	// ȸ�����Խ� ���̵� �ߺ� üũ
	Integer checkId(String memberId);
	// ���� üũ
	Integer checkEmail(String memberEmail);
	
}
