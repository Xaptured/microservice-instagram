package com.thejackfolio.microservices.instagramapi.models;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Post {

    private String id;
    private String caption;
    private String media_url;
    private String media_type;

    public Post() {
    }

    public Post(String id, String caption, String media_url, String media_type) {
        this.id = id;
        this.caption = caption;
        this.media_url = media_url;
        this.media_type = media_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) && Objects.equals(caption, post.caption) && Objects.equals(media_url, post.media_url) && Objects.equals(media_type, post.media_type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, caption, media_url, media_type);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", caption='" + caption + '\'' +
                ", media_url='" + media_url + '\'' +
                ", media_type='" + media_type + '\'' +
                '}';
    }
}
