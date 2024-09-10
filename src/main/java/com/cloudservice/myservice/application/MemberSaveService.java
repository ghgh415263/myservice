package com.cloudservice.myservice.application;

import com.cloudservice.myservice.domain.*;
import com.cloudservice.myservice.ui.MemberSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class MemberSaveService {

    private final MemberRepository memberRepository;
    private final MemberCheckService memberCheckService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordMeter passwordMeter;

    @Transactional
    public void saveMember(MemberSaveRequest memberSaveRequest) {

        if (passwordMeter.isWeak(memberSaveRequest.getPassword()))
            throw new WeakPasswordException();

        if (memberCheckService.isDuplicateUsername(memberSaveRequest.getLoginId()))
            throw new DuplicatedLoginIdException();

        String encodedPassword = passwordEncoder.hashPassword(memberSaveRequest.getPassword());

        Member newMember = Member.builder()
                .loginId(memberSaveRequest.getLoginId())
                .password(encodedPassword)
                .loginProviderType(LoginProviderType.WE)
                .build();

        memberRepository.save(newMember);  //같은 유저네임의 insert가 중복될 경우, 그냥 시스템오류라고 띄워주는 것이 좋다.
    }
}

