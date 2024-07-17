package com.soldesk.ex01.jwt;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.soldesk.ex01.domain.JwtTokenDTO;
import com.soldesk.ex01.domain.MemberCustomDTO;
import com.soldesk.ex01.domain.MemberVO;
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
	
    @Autowired
    private SecretKey key;
    
    @Autowired
    @Qualifier("accessTokenExpiration")
    private Duration accessTokenLife;
    
    @Autowired
    @Qualifier("refreshTokenExpiration")
    private Duration refreshTokenLife;
    
    @Autowired
    private MemberMapper member;

//    @Autowired
//    private Duration refreshTokenLife;
    
    /**
     * 주어진 Authentication 객체를 기반으로 액세스 토큰을 생성합니다.
     * @param auth 인증 객체
     * @return 생성된 액세스 토큰
     */
    public String createAccessToken(String memberId) {
    	log.info("액세스 토큰 발급");
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenLife.toMillis()); // 현재 시간으로부터 유효기간 후의 시간 설정
        
        	
        // JWT accessToken 생성
        String accessToken = Jwts.builder()
            .subject(memberId) // 토큰의 주제 설정 (여기서는 사용자 ID)
            .issuedAt(now) // 토큰 발급 시간 설정
            .claim(AUTHORITIES_KEY, getAuth(memberId)) // 토큰의 권한 설정
            .expiration(expiryDate) // 토큰 만료 시간 설정
            .signWith(key, Jwts.SIG.HS512) // 서명 알고리즘과 시크릿 키로 토큰 서명
            .compact(); // JWT 문자열로 변환하여 반환
        
        return accessToken;
      
        		
    }
    // 토큰권한 체크
    public Authentication getAuthentication(String token) {
    	log.info("토큰 권한 정보 확인");
    	// 토큰의 복호화
    	Claims claims = parseClaims(token);
    	
    	if(claims.get(AUTHORITIES_KEY) == null) {
    		throw new RuntimeException("권한 정보가 없는 토큰입니다.");
    	}
    	
    	MemberVO memberVO = member.selectByMemberId(claims.getSubject());
    	// UserDetails 객체를 만들어서 Authentication 리턴
    	MemberCustomDTO principal = new MemberCustomDTO(memberVO, getAuth(claims.getSubject()));

        return new UsernamePasswordAuthenticationToken(principal, "", getAuth(claims.getSubject()));
    	
    	
    }


	// 토큰에서 사용자 이름 추출 메서드
    public String getUsernameFromToken(String token) {
        return parseClaims(token).getSubject(); // 토큰에서 추출한 클레임 중 사용자 이름 반환
    }

    // 토큰에서 모든 클레임을 추출하는 메서드
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
    public String createRefreshToken(String memberId) {
    	log.info("리프레시 토큰 발급");
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenLife.toMillis()); // 현재 시간으로부터 유효기간 후의 시간 설정
        
        // JWT 생성
        String refreshToken =  Jwts.builder()
            .subject(memberId) // 토큰의 주제 설정 (여기서는 사용자 ID)
            .issuedAt(now) // 토큰 발급 시간 설정
            .expiration(expiryDate) // 토큰 만료 시간 설정
            .signWith(key, Jwts.SIG.HS512) // 서명 알고리즘과 시크릿 키로 토큰 서명
            .compact(); // JWT 문자열로 변환하여 반환
        
        return refreshToken;
    }
    
    /**
     * 리프레시 토큰을 사용하여 새로운 액세스 토큰을 생성합니다.
     * @param refreshToken 리프레시 토큰
     * @return 새로 생성된 액세스 토큰
     */
    public String generateAccessTokenFromRefreshToken(String memberId, String refreshToken) {
    	log.info("리프레시 토큰을 이용한 액세스토큰 재 발급 시작");
    	// 리프레시 토큰에 권한 정보를 저장시켜놓으면 재발급 시키기 매우 쉬워지지만 두개로 나눈 의미가 없어진다.
    	
    	// db에 저장된 리프레시 토큰
    	String checkToken = member.checkToken(memberId);
    	log.info("DB에 저장된 토큰 : " + checkToken);
    	
    	// 전달받은 토큰과 저장된 토큰이 같은지 비교하고 만료된지 아닌지 체크
		if(refreshToken.equals(checkToken) && validateToken(refreshToken)) {
			log.info("토큰 재 검증 및 같은 토큰인지 확인");
			return createAccessToken(memberId); // 통과시 액세스 토큰 재 발급
		}
		
		return null;
    }
}
