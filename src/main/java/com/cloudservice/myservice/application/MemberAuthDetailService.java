package com.cloudservice.myservice.application;

import com.cloudservice.myservice.domain.member.Member;
import com.cloudservice.myservice.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberAuthDetailService {

    private final MemberRepository memberRepository;

    public MemberAuthDetail getMemberAuthDetail(Long id){

        Member member = memberRepository.findById(id)
                .orElseThrow();

        return new MemberAuthDetail(member);
    }

}
