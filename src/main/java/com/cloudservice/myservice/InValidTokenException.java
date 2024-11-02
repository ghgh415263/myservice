package com.cloudservice.myservice;

public class InValidTokenException extends RuntimeException {

    public InValidTokenException(Throwable cause){
        super(cause);
    }
}
