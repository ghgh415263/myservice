package com.cloudservice.myservice.infra;

import com.cloudservice.myservice.domain.PasswordEncoder;
import com.cloudservice.myservice.infra.BcryptPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PasswordConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BcryptPasswordEncoder();
    }
}
