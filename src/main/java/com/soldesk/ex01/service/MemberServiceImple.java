package com.soldesk.ex01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soldesk.ex01.domain.MemberVO;
import com.soldesk.ex01.persistence.MemberMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MemberServiceImple implements MemberService{
	
	@Autowired
	public MemberMapper memberMapper;
	
	@Override
	public int createMember(MemberVO memberVO) {
		log.info("createMember()");
		int result = memberMapper.insert(memberVO);
		return result;
	}

	@Override
	public MemberVO getMemberById(int memberNum) {
		log.info("getMemberById()");
		return memberMapper.selectByMemberId(memberNum);
	}

	@Override
	public List<MemberVO> getAllMember() {
		log.info("getAllMember()");
		return memberMapper.selectIdList();
	}

	@Override
	public int updateMember(MemberVO memberVO) {
		log.info("updateMember()");
		return memberMapper.update(memberVO);
	}

	@Override
	public int updateMemberPermission(MemberVO memberVO) {
		log.info("updateMemberPermission()");
		int result = memberMapper.updateManager(memberVO);
		return result;
	}

	@Override
	public int updateMemberProperty(MemberVO memberVO) {
		log.info("updateMemberProperty()");
		int result = memberMapper.updateProperty(memberVO);
		return result;
	}

	@Override
	public int deleteMember(int memberNum) {
		log.info("deleteMember()");
		return memberMapper.delete(memberNum);
	}

	@Override
	public MemberVO memberCheck(String memberId) {
		log.info("memberCheck()");
		return memberMapper.memberCheck(memberId);
	}

	@Override
	public MemberVO checkId(String memberId) {
		log.info("checkName()");
		return memberMapper.checkId(memberId);
	}
}
