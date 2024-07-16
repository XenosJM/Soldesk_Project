package com.soldesk.ex01.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.soldesk.ex01.domain.MemberVO;
import com.soldesk.ex01.handler.FriendLoginCheckHandler;
import com.soldesk.ex01.jwt.JwtTokenProvider;
import com.soldesk.ex01.persistence.FriendMapper;
import com.soldesk.ex01.persistence.MemberMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class LoginServiceImple implements LoginService {
	
	@Autowired
	private MemberMapper member;
	
	@Autowired
	private FriendMapper friend;

    @Autowired
    private JwtTokenProvider tokenProvider;
    
    private PasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@Override
	public String memberCheckin(Map<String, String> map, HttpServletResponse response) {
	    log.info("memberCheck()");

	    MemberVO memberVO = member.memberCheck(map.get("memberId"));
	    // 인코딩되어 저장된 비밀번호와 입력받은 비밀번호가 일치하는지 확인
	    if (memberVO != null && encoder.matches(map.get("memberPassword"), memberVO.getMemberPassword())) {
	        log.info("비밀번호 검증 성공");
	        String accessToken = tokenProvider.createAccessToken(map.get("memberId"));
	        // JWT 액세스 토큰을 Authorization 헤더에 추가
	        response.setHeader("Authorization", "Bearer " + accessToken);
        	// 로그인 성공시 로그인 상태 업데이트
	        friend.friendStateChange(map.get("memberId"), "online");
	        
	        // 자동 로그인(30일 또는 7일 등) 설정시 true를 전달
	        if(map.get("rememberMe").contains("true")) {
	        	String refreshToken = tokenProvider.createRefreshToken(map.get("memberId"));
	        	// JWT 리프레시 토큰을 따로 정의한 헤더에 추가
	        	memberVO.setRefreshToken(refreshToken);
	        	int result = member.updateRefreshToken(memberVO);
	        	log.info("자동 로그인 리프레시 토큰 업데이트 결과 : " + result);
	        	response.setHeader("Refresh-Token", refreshToken );
	        	// 로그인 성공시 로그인 상태 업데이트
	        	friend.friendStateChange(map.get("memberId"), "online");
	        	
	        }
	        String memberRole = member.memberRole(memberVO.getMemberId());
	        

	        return memberRole;
	    } else {
	        return "fail";
	    }
	}
	
	// TODO 자동 로그인시 리프레시 토큰을 검증하고 로그인 되었다고 알려주는 로직 짤것

//	@Override
//	public String memberCheckout(String memberId) {
//		
//		return null;
//	}
}
