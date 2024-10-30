package com.cloudservice.myservice.domain.member;

import org.springframework.stereotype.Component;

@Component
public class PasswordMeter {

    private String specialCharactersString = "!@#$%&*()'+,-./:;<=>?[]^_`{|}";

    public Boolean isWeak(String password) {
        if (!hasNumber(password))
            return true;
        if (!hasSpecialCharacter(password))
            return true;
        return false;
    }

    private boolean hasNumber(String password) {
        for (char c : password.toCharArray()) {
            if(Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasSpecialCharacter(String password) {
        for (char c : password.toCharArray()) {
            if(specialCharactersString.contains(Character.toString(c))) {
                return true;
            }
        }
        return false;
    }
}
