package com.cloudservice.myservice.infra;

import com.cloudservice.myservice.domain.member.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PasswordConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BcryptPasswordEncoder();
    }
}
