package com.soldesk.ex01.util;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails, Serializable {

	 private static final long serialVersionUID = 174726374856727L;

	    private String memberId;		// ���̵�
	    private String memberPassword;	// ��й�ȣ
	    private String memberEmail;	// �̸���
	    private String role;	// ��� ����
	    private boolean locked;	// ���� ��� ����
	    private Collection<GrantedAuthority> auth;	//���� ���
	
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
     * ���� ���� ����
     * true : ���� �ȵ�
     * false : ����
     * @return
     */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	 /**
     * ���� ��� ����
     * true : ����� ����
     * false : ���
     * @return
     */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	/**
     * ��й�ȣ ���� ����
     * true : ���� �ȵ�
     * false : ����
     * @return
     */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	/**
     * ����� Ȱ��ȭ ����
     * ture : Ȱ��ȭ
     * false : ��Ȱ��ȭ
     * @return
     */
	@Override
	public boolean isEnabled() {
		//�̸����� �����Ǿ� �ְ� ������ ������� ������ true
        return (!locked);
	}

}
