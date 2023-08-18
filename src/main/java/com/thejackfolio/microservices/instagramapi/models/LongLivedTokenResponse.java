package com.thejackfolio.microservices.instagramapi.models;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LongLivedTokenResponse {

    private String access_token;
    private String token_type;
    private Integer expires_in;

    public LongLivedTokenResponse() {
    }

    public LongLivedTokenResponse(String access_token, String token_type, Integer expires_in) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LongLivedTokenResponse that = (LongLivedTokenResponse) o;
        return expires_in == that.expires_in && Objects.equals(access_token, that.access_token) && Objects.equals(token_type, that.token_type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(access_token, token_type, expires_in);
    }

    @Override
    public String toString() {
        return "LongLivedTokenResponse{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", expires_in=" + expires_in +
                '}';
    }
}
