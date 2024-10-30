package com.cloudservice.myservice.domain.member;

public interface PasswordEncoder {
    String hashPassword(String raw);
    boolean isMatch(String raw, String hashed);
}
