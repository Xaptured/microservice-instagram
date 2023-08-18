package com.thejackfolio.microservices.instagramapi.models;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class InstagramPostsResponseWrapper {

    private InstagramPostsResponse response;
    private String message;

    public InstagramPostsResponseWrapper() {
    }

    public InstagramPostsResponseWrapper(InstagramPostsResponse response, String message) {
        this.response = response;
        this.message = message;
    }

    public InstagramPostsResponse getResponse() {
        return response;
    }

    public void setResponse(InstagramPostsResponse response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstagramPostsResponseWrapper that = (InstagramPostsResponseWrapper) o;
        return Objects.equals(response, that.response) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(response, message);
    }

    @Override
    public String toString() {
        return "InstagramPostsResponseWrapper{" +
                "response=" + response +
                ", message='" + message + '\'' +
                '}';
    }
}
