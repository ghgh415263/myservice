package com.cloudservice.myservice.application;

import com.cloudservice.myservice.domain.member.PasswordEncoder;
import com.cloudservice.myservice.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberLoginService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public Long login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(member -> passwordEncoder.isMatch(password, member.getPassword()))
                .map(member -> member.getId())
                .orElse(null);
    }
}
