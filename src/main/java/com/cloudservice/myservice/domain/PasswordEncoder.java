package com.cloudservice.myservice.domain;

public interface PasswordEncoder {
    String hashPassword(String raw);
    boolean isMatch(String raw, String hashed);
}
