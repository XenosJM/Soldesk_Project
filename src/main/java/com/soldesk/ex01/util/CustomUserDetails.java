package com.soldesk.ex01.util;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails, Serializable {

	 private static final long serialVersionUID = 174726374856727L;

	    private String memberId;		// 아이디
	    private String memberPassword;	// 비밀번호
	    private String memberEmail;	// 이메일
	    private String role;	// 멤버 역할
	    private boolean locked;	// 계정 잠김 여부
	    private Collection<GrantedAuthority> auth;	//권한 목록
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return auth;
	}

	@Override
	public String getPassword() {
		return memberPassword;
	}

	@Override
	public String getUsername() {
		return memberId;
	}
	
	 /**
     * 계정 만료 여부
     * true : 만료 안됨
     * false : 만료
     * @return
     */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	 /**
     * 계정 잠김 여부
     * true : 잠기지 않음
     * false : 잠김
     * @return
     */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	/**
     * 비밀번호 만료 여부
     * true : 만료 안됨
     * false : 만료
     * @return
     */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	/**
     * 사용자 활성화 여부
     * ture : 활성화
     * false : 비활성화
     * @return
     */
	@Override
	public boolean isEnabled() {
		//이메일이 인증되어 있고 계정이 잠겨있지 않으면 true
        return (!locked);
	}

}
