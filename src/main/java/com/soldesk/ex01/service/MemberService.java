package com.soldesk.ex01.service;

import java.util.List;

import com.soldesk.ex01.domain.MemberVO;

public interface MemberService {
	
	// ȸ�� ����
	int createMember(MemberVO memberVO);
	// ȸ�� �ڱ� ���� ����
	MemberVO getMemberById(int memberId);
	// ȸ�� �˻�
	List<MemberVO> getAllMember();
	// ȸ�� ���� ����
	int updateMember(MemberVO memberVO);
	// ȸ�� ���� ����(Ż��)
	int deleteMember(int memberId);
	// TODO ȸ�� ģ�� ���� -> ģ�� ���񽺿���
	
	// TODO ȸ�� ���� ������ -> ������ ���񽺿��� 
	
}
