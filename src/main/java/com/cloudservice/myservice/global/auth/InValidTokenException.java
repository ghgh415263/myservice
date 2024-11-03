package com.cloudservice.myservice.global.auth;

public class InValidTokenException extends RuntimeException {

    public InValidTokenException(Throwable cause){
        super(cause);
    }
}
