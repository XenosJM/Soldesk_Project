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

    public static class Builder {
        private final JwtTokenDTO jwtTokenDTO;

        private Builder() {
            jwtTokenDTO = new JwtTokenDTO();
        }

        public Builder grantType(String grantType) {
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

        public JwtTokenDTO build() {
            return jwtTokenDTO;
        }
    }
}
