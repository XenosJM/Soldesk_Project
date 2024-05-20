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
	int updateProperty(MemberVO memberVO);
	int updateManager(MemberVO memberVO);
	int delete(String memberId);
	MemberVO memberCheck(String memberId);
	String checkId(String memberId);
	Integer checkEmail(String memberEmail);
	MemberVO findId(String memberEmail);
	
}
