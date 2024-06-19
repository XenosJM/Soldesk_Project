package com.soldesk.ex01.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.soldesk.ex01.domain.MemberVO;

public interface MemberService {
	
	// ȸ�� ����
	int createMember(MemberVO memberVO);
	// ȸ�� �ڱ� ���� ����
	MemberVO getMemberById(String memberId);
	// ȸ�� �˻�
	List<MemberVO> getAllMember();
	// ȸ�� ��й�ȣ ����
	int updatePassword(MemberVO memberVO);
	// ȸ�� �̸��� ����
	int updateEmail(MemberVO memberVO);
	// ȸ�� ������ ���� ����
	int updateMemberPermission(MemberVO memberVO);
	// ȸ�� ���� ������ ����
	int updateMemberProperty(MemberVO memberVO);
	// ȸ�� ���� ����(Ż��)
	int deleteMember(String memberId);
	// �α���
	int memberCheck(Map<String, String> res, HttpSession session);
	// TODO ȸ�� ģ�� ���� -> ģ�� ���񽺿���
	
	// ģ�� ��û(���)
	
	// ģ�� 
	// TODO ȸ�� ���� ������ -> ������ ���񽺿��� 
	
}
