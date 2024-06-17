package com.soldesk.ex01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.soldesk.ex01.persistence.MemberMapper;

public class MemberDetailsServiceImple implements UserDetailsService {
	
	@Autowired
	private MemberMapper member;
	
	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		
		return null;
	}

}
