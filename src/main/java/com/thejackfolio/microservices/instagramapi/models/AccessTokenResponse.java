package com.thejackfolio.microservices.instagramapi.models;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AccessTokenResponse {

    private String access_token;
    private Long user_id;

    public AccessTokenResponse() {
    }

    public AccessTokenResponse(String access_token, Long user_id) {
        this.access_token = access_token;
        this.user_id = user_id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessTokenResponse that = (AccessTokenResponse) o;
        return Objects.equals(access_token, that.access_token) && Objects.equals(user_id, that.user_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(access_token, user_id);
    }

    @Override
    public String toString() {
        return "AccessTokenResponse{" +
                "access_token='" + access_token + '\'' +
                ", user_id=" + user_id +
                '}';
    }
}
