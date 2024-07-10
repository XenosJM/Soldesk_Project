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
import com.soldesk.ex01.persistence.MemberMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class LoginServiceImple implements LoginService {
	
	@Autowired
	private MemberMapper member;

    @Autowired
    private JwtTokenProvider tokenProvider;
        
    private FriendLoginCheckHandler loginCheck;
    
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
	        // 자동 로그인(30일 또는 7일 등) 설정시 true를 전달
	        if(map.get("rememberMe").contains("true")) {
	        	String refreshToken = tokenProvider.createRefreshToken(map.get("memberId"));
	        	// JWT 리프레시 토큰을 따로 정의한 헤더에 추가
	        	memberVO.setRefreshToken(refreshToken);
	        	member.updateRefreshToken(memberVO);
	        	response.setHeader("Refresh-Token", refreshToken );
	        	
	        }
//	        loginCheck.onLogin(map.get("memberId"));

	        return "success";
	    } else {
	        return "fail";
	    }
	}

	@Override
	public String memberCheckout(String memberId) {
		// TODO Auto-generated method stub
		// 웹 소켓의 연결이 끊어지면 로그아웃 처리 되도록 진행할것 로그아웃 처리되면서 logout 알림 뜨게 만들기.
		
		return null;
	}
}
