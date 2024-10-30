package com.cloudservice.myservice.application;

import com.cloudservice.myservice.domain.member.LoginProviderType;
import com.cloudservice.myservice.domain.member.Member;
import com.cloudservice.myservice.domain.member.MemberRepository;
import com.cloudservice.myservice.domain.member.KaKaoAccessTokenRes;
import com.cloudservice.myservice.domain.member.KakaoMember;
import com.cloudservice.myservice.domain.member.OauthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class OauthService {

    private final MemberRepository memberRepository;

    private final OauthProvider oauthProvider;

    public Long login(String code){
        KaKaoAccessTokenRes res = oauthProvider.getAccessToken(code);

        KakaoMember memberRes = oauthProvider.getMember(res.getAccessToken());

        Long id = memberRes.getId();

        return memberRepository.findByOauthId(Long.toString(id))
                .map(member -> member.getId())
                .orElseGet(() -> createMember(id).getId());
    }

    private Member createMember(Long OauthId) {
        Member member = Member.builder()
                .loginProviderType(LoginProviderType.KAKAO)
                .oauthId(Long.toString(OauthId))
                .build();
        return memberRepository.save(member);
    }
}
