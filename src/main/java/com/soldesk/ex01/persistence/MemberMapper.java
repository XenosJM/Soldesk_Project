package com.soldesk.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soldesk.ex01.domain.MemberVO;

@Mapper
public interface MemberMapper {
	int insert(MemberVO memberVO);
	MemberVO selectByMemberId(String memberId);
	List<MemberVO> selectIdList();
	int update(MemberVO memberVO);
	int updatePassword(MemberVO memberVO);
	int updateEmail(MemberVO memberVO);
	int updateProperty(MemberVO memberVO);
	int updateManager(MemberVO memberVO);
	int delete(String memberId);
	MemberVO memberCheck(String memberId);
	String checkId(String memberId);
	String checkEmail(String memberEmail);
	MemberVO findId(String memberEmail);
	String memberRole(String memberId);
	String checkToken(String memberId);
	int updateRefreshToken(MemberVO memberVO);
	int updateRole(MemberVO memberVO);
	// 멤버 롤 정보에서 카테고리id 가져오는 매퍼
	int memberCategory(String memberId);
	// 멤버 검색
	List<MemberVO> findMemberId(String memberId);
	
}
