package com.cloudservice.myservice.domain.member;

public class WeakPasswordException extends RuntimeException{
    public WeakPasswordException(){
        super("숫자와 특수문자를 각각 하나 이상 포함해주세요.");
    }
}
