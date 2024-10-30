package com.cloudservice.myservice.domain.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class MemberCheckService {

    private final MemberRepository memberRepository;

    public Boolean isDuplicateUsername(String loginId) {
        if (memberRepository.findByLoginId(loginId).isPresent())
            return true;
        return false;
    }
}
