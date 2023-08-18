package com.thejackfolio.microservices.instagramapi.models;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Cursors {

    private String before;
    private String after;

    public Cursors() {
    }

    public Cursors(String before, String after) {
        this.before = before;
        this.after = after;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cursors cursors = (Cursors) o;
        return Objects.equals(before, cursors.before) && Objects.equals(after, cursors.after);
    }

    @Override
    public int hashCode() {
        return Objects.hash(before, after);
    }

    @Override
    public String toString() {
        return "Cursors{" +
                "before='" + before + '\'' +
                ", after='" + after + '\'' +
                '}';
    }
}
