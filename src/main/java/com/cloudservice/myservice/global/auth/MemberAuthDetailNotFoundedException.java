package com.cloudservice.myservice.global.auth;

public class MemberAuthDetailNotFoundedException extends RuntimeException{

    public MemberAuthDetailNotFoundedException() {
        super("MemberAuthDetailHolder에서 MemberAuthDetail을 찾을 수 없습니다.");
    }
}
