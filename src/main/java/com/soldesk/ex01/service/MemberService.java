package com.soldesk.ex01.service;

import java.util.List;

import com.soldesk.ex01.domain.MemberVO;

public interface MemberService {
	
	// 회원 가입
	int createMember(MemberVO memberVO);
	// 회원 자기 정보 보기
	MemberVO getMemberById(int memberId);
	// 회원 검색
	List<MemberVO> getAllMember();
	// 회원 정보 수정
	int updateMember(MemberVO memberVO);
	// 회원 정보 삭제(탈퇴)
	int deleteMember(int memberId);
	// TODO 회원 친구 보기 -> 친구 서비스에서
	
	// TODO 회원 쪽지 보내기 -> 쪽지용 서비스에서 
	
}
