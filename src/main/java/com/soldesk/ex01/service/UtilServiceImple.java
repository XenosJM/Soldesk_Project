package com.soldesk.ex01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soldesk.ex01.domain.MemberVO;
import com.soldesk.ex01.persistence.MemberMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class UtilServiceImple implements UtilService {
	
	@Autowired
	private MemberMapper memberMapper;

	private PasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Override
	public String checkId(String memberId) {
		log.info("checkId()");
		return memberMapper.checkId(memberId);
	}

	@Override
	public String checkEmail(String memberEmail) {
		log.info("checkEmail()");
		return memberMapper.checkEmail(memberEmail);
	}  // end checkEmail
	
	@Transactional
	@Override
	public int updatePassword(MemberVO memberVO) {
		log.info("updatePw");
		String encodedPassword = encoder.encode(memberVO.getMemberPassword());
		memberVO.setMemberPassword(encodedPassword);
		int result = memberMapper.updatePassword(memberVO);
		return result;
	}
}
