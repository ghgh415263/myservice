package com.cloudservice.myservice.application;

public class DuplicatedLoginIdException extends RuntimeException {
    public DuplicatedLoginIdException(){
        super("중복된 유저네임입니다.");
    }
}
