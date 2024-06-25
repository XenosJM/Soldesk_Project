package com.soldesk.ex01.jwt;

import java.security.Key;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.soldesk.ex01.domain.JwtTokenDTO;
import com.soldesk.ex01.domain.MemberCustomDTO;
import com.soldesk.ex01.persistence.MemberMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j;

@Component
@Log4j
public class JwtTokenProvider {

	private static final String AUTHORITIES_KEY = "auth";
	private static final String BEARER_TYPE = "Bearer";
	
    @Autowired
    private SecretKey key;
    
    @Autowired
    private Duration accessTokenLife;
    
    @Autowired
    private MemberMapper member;

//    @Autowired
//    private Duration refreshTokenLife;
    
    /**
     * 주어진 Authentication 객체를 기반으로 액세스 토큰을 생성합니다.
     * @param auth 인증 객체
     * @return 생성된 액세스 토큰
     */
    public JwtTokenDTO createAccessToken(Authentication auth) {
    	log.info("액세스 토큰 발급");
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenLife.toMillis()); // 현재 시간으로부터 유효기간 후의 시간 설정
        
        String authrities = auth.getAuthorities().stream()
        		.map(GrantedAuthority::getAuthority)
        		.collect(Collectors.joining(","));
        	
        // JWT accessToken 생성
        String accessToken = Jwts.builder()
                .subject(auth.getName()) // 토큰의 주제 설정 (여기서는 사용자 ID)
                .issuedAt(now) // 토큰 발급 시간 설정
                .claim(AUTHORITIES_KEY, authrities) // 토큰의 권한 설정
                .expiration(expiryDate) // 토큰 만료 시간 설정
                .signWith(key, Jwts.SIG.HS512) // 서명 알고리즘과 시크릿 키로 토큰 서명
                .compact(); // JWT 문자열로 변환하여 반환
        
        return JwtTokenDTO.builder()
        		.grantType(BEARER_TYPE)
        		.accessToken(accessToken)
        		.accessTokenExpiresIn(expiryDate)
        		.build();
        		
    }
    
    public Authentication getAuthentication(String token) {
    	// 토큰의 복호화
    	Claims claims = parseClaims(token);
    	
    	if(claims.get(AUTHORITIES_KEY) == null) {
    		throw new RuntimeException("권한 정보가 없는 토큰입니다.");
    	}
    	
    	// UserDetails 객체를 만들어서 Authentication 리턴
       MemberCustomDTO principal = new MemberCustomDTO(claims.getSubject(), "", getAuth(claims.getSubject()));

        return new UsernamePasswordAuthenticationToken(principal, "", getAuth(claims.getSubject()));
    	
    	
    }


	// 토큰에서 사용자 이름 추출 메서드
    public String getUsernameFromToken(String token) {
        return parseClaims(token).getSubject(); // 토큰에서 추출한 클레임 중 사용자 이름 반환
    }

    // 토큰에서 모든 클레임을 추출하는 메서드 // TODO 예외상황도 생각해볼것
    private Claims parseClaims(String token) {
        // 토큰을 복호화 파싱하고, 서명 검증 후 클레임 추출
        Claims claims = Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token).getPayload();
        return claims; // 추출된 클레임 반환
    }

    // JWT 유효성 검사
    public boolean validateToken(String authToken) {
        try {
            // 주어진 토큰을 파싱하고, 서명 검증을 통해 유효성 검사
            Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(authToken);
            return true; // 유효한 토큰일 경우 true 반환
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
    
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
    
    /**
     * 주어진 Authentication 객체를 기반으로 리프레시 토큰을 생성합니다.
     * @param auth 인증 객체
     * @return 생성된 리프레시 토큰
     */
//    public String createRefreshToken(String memberId) {
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + refreshTokenLife.toMillis()); // 현재 시간으로부터 유효기간 후의 시간 설정
//        
//        // JWT 생성
//        return Jwts.builder()
//            .subject(memberId) // 토큰의 주제 설정 (여기서는 사용자 ID)
//            .issuedAt(now) // 토큰 발급 시간 설정
//            .expiration(expiryDate) // 토큰 만료 시간 설정
//            .signWith(secretKey) // 서명 알고리즘과 시크릿 키로 토큰 서명
//            .compact(); // JWT 문자열로 변환하여 반환
//    }
    
    /**
     * 리프레시 토큰을 사용하여 새로운 액세스 토큰을 생성합니다.
     * @param refreshToken 리프레시 토큰
     * @return 새로 생성된 액세스 토큰
     */
//    public String generateAccessTokenFromRefreshToken(String refreshToken) {
//        String username = getUsernameFromToken(refreshToken); // 리프레시 토큰에서 사용자 이름 추출
//        
//        // 새로운 액세스 토큰 생성
//        return createAccessToken(username);
//    }
}
