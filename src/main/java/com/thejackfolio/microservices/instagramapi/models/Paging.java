package com.thejackfolio.microservices.instagramapi.models;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Paging {

    private Cursors cursors;
    private String next;

    public Paging() {
    }

    public Paging(Cursors cursors, String next) {
        this.cursors = cursors;
        this.next = next;
    }

    public Cursors getCursors() {
        return cursors;
    }

    public void setCursors(Cursors cursors) {
        this.cursors = cursors;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paging paging = (Paging) o;
        return Objects.equals(cursors, paging.cursors) && Objects.equals(next, paging.next);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cursors, next);
    }

    @Override
    public String toString() {
        return "Paging{" +
                "cursors=" + cursors +
                ", next='" + next + '\'' +
                '}';
    }
}
