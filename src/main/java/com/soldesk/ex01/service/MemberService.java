package com.soldesk.ex01.service;

import java.util.List;

import com.soldesk.ex01.domain.MemberVO;

public interface MemberService {
	
	// ȸ�� ����
	int createMember(MemberVO memberVO);
	// ȸ�� �ڱ� ���� ����
	MemberVO getMemberById(int memberNum);
	// ȸ�� �˻�
	List<MemberVO> getAllMember();
	// ȸ�� ���� ����
	int updateMember(MemberVO memberVO);
	// ȸ�� ������ ���� ����
	int updateMemberPermission(MemberVO memberVO);
	// ȸ�� ���� ������ ����
	int updateMemberProperty(MemberVO memberVO);
	// ȸ�� ���� ����(Ż��)
	int deleteMember(int memberNum);
	// �α���
	MemberVO memberCheck(String memberId);
	// ȸ�����Խ� ���̵� �ߺ� üũ
	MemberVO checkId(String memberId);
	// TODO ȸ�� ģ�� ���� -> ģ�� ���񽺿���
	
	// ģ�� ��û(���)
	
	// ģ�� 
	// TODO ȸ�� ���� ������ -> ������ ���񽺿��� 
	
}
