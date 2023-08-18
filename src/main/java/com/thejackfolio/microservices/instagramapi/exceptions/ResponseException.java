package com.thejackfolio.microservices.instagramapi.exceptions;

public class ResponseException extends Exception{

    public ResponseException(String message, Throwable exception){
        super(message, exception);
    }
}
