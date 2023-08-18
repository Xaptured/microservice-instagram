/*
 * Copyright (c) 2023.
 * Created this for the project called "TheJackFolio"
 * All right reserved by Jack
 */

package com.thejackfolio.microservices.instagramapi.models;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Objects;

@Component
public class Instagram_Token {

    private String token;
    private Date creationDate;
    private Date expirationDate;
    private String message;

    public Instagram_Token() {
    }

    public Instagram_Token(String token, Date creationDate, Date expirationDate, String message) {
        this.token = token;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
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
        Instagram_Token that = (Instagram_Token) o;
        return Objects.equals(token, that.token) && Objects.equals(creationDate, that.creationDate) && Objects.equals(expirationDate, that.expirationDate) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, creationDate, expirationDate, message);
    }

    @Override
    public String toString() {
        return "Instagram_Token{" +
                "token='" + token + '\'' +
                ", creationDate=" + creationDate +
                ", expirationDate=" + expirationDate +
                ", message='" + message + '\'' +
                '}';
    }
}
