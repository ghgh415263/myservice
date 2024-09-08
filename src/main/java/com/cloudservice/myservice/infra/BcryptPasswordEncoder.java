package com.cloudservice.myservice.infra;

import com.cloudservice.myservice.domain.PasswordEncoder;
import org.mindrot.jbcrypt.BCrypt;

public class BcryptPasswordEncoder implements PasswordEncoder {
    @Override
    public String hashPassword(String plaintext) {
        return BCrypt.hashpw(plaintext, BCrypt.gensalt());
    }

    @Override
    public boolean isMatch(String plaintext, String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }
}
