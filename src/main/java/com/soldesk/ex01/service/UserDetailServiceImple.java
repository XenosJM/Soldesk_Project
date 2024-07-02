package com.soldesk.ex01.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.soldesk.ex01.domain.MemberCustomDTO;
import com.soldesk.ex01.domain.MemberVO;
import com.soldesk.ex01.persistence.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

//로그인시, security filter가 가로채서 호출하는 서비스
@Log4j
@Service
@RequiredArgsConstructor
public class UserDetailServiceImple implements UserDetailsService, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MemberMapper member;
	
	// 가로챈 form 데이터의 memberId를 가져와서 사용
	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		log.info("security userdetail");
		log.info("memberId : " + memberId);
		// 멤버인지 확인
		MemberVO memberVO = member.memberCheck(memberId);
		log.info("security MemberVO : " + memberVO);
		// 널값이 아니라면
		if(memberVO == null) {
			throw new UsernameNotFoundException("memberId를 찾을수 없습니다..");
		}
		// VO의 List형태가 아닌 roleId 추가하기위한 메서드 작성
		Collection<? extends GrantedAuthority> auth = getAuth(memberVO.getMemberId());
		log.info("auth : " + auth);
		return new MemberCustomDTO(memberVO, auth);
	}
	// 멤버 역할에 추가될 역할 이름을 가져오는 쿼리로 가져와 담고
	public Collection<? extends GrantedAuthority> getAuth(String memberId) {
		log.info("getAuth 권한 가져오기");
		String memberRole = member.memberRole(memberId);
		List<GrantedAuthority> auth = new ArrayList<>();
		// 가져온 역할이 멤버인 경우 
		if(memberRole != null) {
			auth.add(new SimpleGrantedAuthority("ROLE_" + memberRole));
		} else {
//			// 가져온 역할이 멤버가 아닌 관리자 쪽인 경우
//			auth.add(new SimpleGrantedAuthority("ROLE_" + memberRole));
//			auth.add(new SimpleGrantedAuthority("ROLE_" + "MEMBER"));
			throw new AuthenticationCredentialsNotFoundException("권한이 없는 사용자 입니다.");
			
		}
		log.info(auth);
		return auth;
	}

}
