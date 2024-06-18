package com.soldesk.ex01.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.soldesk.ex01.domain.MemberCustomDTO;
import com.soldesk.ex01.domain.MemberVO;
import com.soldesk.ex01.persistence.MemberMapper;

import lombok.extern.log4j.Log4j;

// �α��ν�, security filter�� ����ä�� ȣ���ϴ� ����
@Log4j
@Service
public class UserDetailServiceImple implements UserDetailsService, Serializable {
	
	@Autowired
	private MemberMapper member;
	
	// ����æ form �������� memberId�� �����ͼ� ���
	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		log.info("security userdetail");
		log.info(memberId);
		// ������� Ȯ��
		MemberVO memberVO = member.memberCheck(memberId);
		log.info("security MemberVO : " + memberVO);
		// ��üũ
		if(memberVO == null) {
			throw new UsernameNotFoundException("memberId�� ã���� �����ϴ�.");
		}
		// List���°� �ƴ� VO�� roleId �߰��ϱ����� �޼��� �ۼ�
		Collection<? extends GrantedAuthority> auth = getAuth(memberVO.getMemberId());
		log.info("auth : " + auth);
		return new MemberCustomDTO(memberVO, auth);
	}
	// ��� ���ҿ� �߰��� ���� �̸��� �������� ������ ������ ��� 
	private Collection<? extends GrantedAuthority> getAuth(String memberId) {
		log.info("getAuth ���� ��������");
		String memberRole = member.memberRole(memberId);
		List<GrantedAuthority> auth = new ArrayList<>();
		// ������ ������ ����� ��� 
		if(memberRole.contains("MEMBER")) {
			auth.add(new SimpleGrantedAuthority("ROLE_" + memberRole));
		// ������ ������ ����� �ƴ� ������ ���� ���
		} else {
			auth.add(new SimpleGrantedAuthority("ROLE_" + memberRole));
			auth.add(new SimpleGrantedAuthority("ROLE_" + "MEMBER"));
		}
		log.info(auth);
		return auth;
	}

}
