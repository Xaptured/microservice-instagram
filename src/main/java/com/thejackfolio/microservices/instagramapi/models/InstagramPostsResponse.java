package com.thejackfolio.microservices.instagramapi.models;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class InstagramPostsResponse {

    private List<Post> data;
    private Paging paging;

    public InstagramPostsResponse() {
    }

    public InstagramPostsResponse(List<Post> data, Paging paging) {
        this.data = data;
        this.paging = paging;
    }

    public List<Post> getData() {
        return data;
    }

    public void setData(List<Post> data) {
        this.data = data;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstagramPostsResponse that = (InstagramPostsResponse) o;
        return Objects.equals(data, that.data) && Objects.equals(paging, that.paging);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, paging);
    }

    @Override
    public String toString() {
        return "InstagramPostsResponse{" +
                "data=" + data +
                ", paging=" + paging +
                '}';
    }
}
