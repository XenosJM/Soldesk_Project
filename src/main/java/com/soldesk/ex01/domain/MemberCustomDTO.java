package com.soldesk.ex01.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class MemberCustomDTO extends User {
	
	private static final long serialVersionUID = 1L;
	
	private MemberVO member;
	
	public MemberCustomDTO(MemberVO memberVO, Collection<? extends GrantedAuthority> auth) {
		super(memberVO.getMemberId(), memberVO.getMemberPassword(), auth);
		 this.member = memberVO;
	}
	
	public MemberVO getMember() {
		return member;
	}
	
	

}
