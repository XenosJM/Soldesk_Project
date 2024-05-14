package com.soldesk.ex01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soldesk.ex01.domain.MemberVO;
import com.soldesk.ex01.persistence.MemberMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class UtilServiceImple implements UtilService {
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public MemberVO checkId(String memberId) {
		log.info("checkId()");
		return memberMapper.checkId(memberId);
	}

	@Override
	public MemberVO checkEmail(String memberEmail) {
		log.info("checkEmail()");
		return memberMapper.checkEmail(memberEmail);
	}  // end checkEmail

}
