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
		super(memberVO.getMemberId(), "", auth); // payload 부분에 들어갈 정보로는 이름과 권한만 들어가도록 설정함.
		 this.member = memberVO;
	}
	

	public MemberCustomDTO(String subject, String string, Collection<? extends GrantedAuthority> auth) {
		super(subject, "", auth);
	}


	public MemberVO getMember() {
		return member;
	}
	
	

}
