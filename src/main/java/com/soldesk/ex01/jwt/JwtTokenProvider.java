package com.soldesk.ex01.jwt;

import java.time.Duration;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenProvider {

    @Autowired
    private SecretKey secretKey;
    
    @Autowired
    private Duration accessTokenLife;

//    @Autowired
//    private Duration refreshTokenLife;
    
  
    /**
     * 주어진 Authentication 객체를 기반으로 액세스 토큰을 생성합니다.
     * @param auth 인증 객체
     * @return 생성된 액세스 토큰
     */
    public String createAccessToken(String memberId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenLife.toMillis()); // 현재 시간으로부터 유효기간 후의 시간 설정
        
        // JWT 생성
        return Jwts.builder()
            .subject(memberId) // 토큰의 주제 설정 (여기서는 사용자 ID)
            .issuedAt(now) // 토큰 발급 시간 설정
            .expiration(expiryDate) // 토큰 만료 시간 설정
            .signWith(secretKey) // 서명 알고리즘과 시크릿 키로 토큰 서명
            .compact(); // JWT 문자열로 변환하여 반환
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

    // 토큰에서 사용자 이름 추출 메서드
    public String getUsernameFromToken(String token) {
        return extractAllClaims(token).getSubject(); // 토큰에서 추출한 클레임 중 사용자 이름 반환
    }

    // 토큰에서 모든 클레임을 추출하는 메서드
    private Claims extractAllClaims(String token) {
        // 토큰을 파싱하고, 서명 검증 후 클레임 추출
        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        return claims; // 추출된 클레임 반환
    }

    // JWT 유효성 검사
    public boolean validateToken(String authToken) {
        try {
            // 주어진 토큰을 파싱하고, 서명 검증을 통해 유효성 검사
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(authToken).getPayload();
            return true; // 유효한 토큰일 경우 true 반환
        } catch (Exception e) {
            return false; // 유효하지 않은 토큰일 경우 false 반환
        }
    }
    
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
