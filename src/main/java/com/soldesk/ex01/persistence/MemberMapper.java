package com.soldesk.ex01.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.soldesk.ex01.domain.MemberVO;

@Mapper
public interface MemberMapper {
	int insert(MemberVO memberVO);
	MemberVO selectByMemberId(int memberNum);
	List<MemberVO> selectIdList();
	int update(MemberVO memberVO);
	int updateProperty(MemberVO memberVO);
	int updateManager(MemberVO memberVO);
	int delete(int memberNum);
	MemberVO memberCheck(String memberId);
	MemberVO checkId(String memberId);
	MemberVO checkEmail(String memberEmail);
}
