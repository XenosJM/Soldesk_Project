package com.soldesk.ex01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.soldesk.ex01.domain.MemberVO;

public class MemberServiceImple implements MemberService{
	
	@Autowired
	public MemberVO memberVO;
	
	@Override
	public int createMember(MemberVO memberVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MemberVO getMemberById(int memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemberVO> getAllMember() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateMember(MemberVO memberVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteMember(int memberId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
