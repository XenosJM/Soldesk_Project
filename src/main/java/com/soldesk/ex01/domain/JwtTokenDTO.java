package com.soldesk.ex01.domain;

import java.util.Date;

public class JwtTokenDTO {
    private String grantType;
    private String accessToken;
    private Date accessTokenExpiresIn;

    private JwtTokenDTO() {}

    public static Builder builder() {
        return new Builder();
    }

    public String getGrantType() {
        return grantType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Date getAccessTokenExpiresIn() {
        return accessTokenExpiresIn;
    }

    // DTO에 데이터를 set하기위한 함수명
    public static class Builder {
    	// 실제 데이터 전송에 사용되는 DTO 객체 호출
        private final JwtTokenDTO jwtTokenDTO;
        
        private Builder() {
        	// 호출된 객체 초기화
            jwtTokenDTO = new JwtTokenDTO();
        }
        // 설정받은(입력받은) 값을 초기화된 객체에 저장
        public Builder grantType(String grantType) {
        	// 헤더에 토큰 타입을 지정하기 위한 메서드
            jwtTokenDTO.grantType = grantType;
            return this;
        }

        public Builder accessToken(String accessToken) {
            jwtTokenDTO.accessToken = accessToken;
            return this;
        }

        public Builder accessTokenExpiresIn(Date expiryDate) {
            jwtTokenDTO.accessTokenExpiresIn = expiryDate;
            return this;
        }
        // 세팅이 끝난 DTO 객체 리턴
        public JwtTokenDTO build() {
            return jwtTokenDTO;
        }
    }
}
