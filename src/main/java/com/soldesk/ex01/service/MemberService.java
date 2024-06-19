package com.soldesk.ex01.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.soldesk.ex01.domain.MemberVO;

public interface MemberService {
	
	// 회원 가입
	int createMember(MemberVO memberVO);
	// 회원 자기 정보 보기
	MemberVO getMemberById(String memberId);
	// 회원 검색
	List<MemberVO> getAllMember();
	// 회원 비밀번호 수정
	int updatePassword(MemberVO memberVO);
	// 회원 이메일 수정
	int updateEmail(MemberVO memberVO);
	// 회원 관리자 권한 변경
	int updateMemberPermission(MemberVO memberVO);
	// 회원 보유 아이템 변경
	int updateMemberProperty(MemberVO memberVO);
	// 회원 정보 삭제(탈퇴)
	int deleteMember(String memberId);
	// 로그인
	int memberCheck(Map<String, String> res, HttpSession session);
	// TODO 회원 친구 보기 -> 친구 서비스에서
	
	// 친구 신청(등록)
	
	// 친구 
	// TODO 회원 쪽지 보내기 -> 쪽지용 서비스에서 
	
}
