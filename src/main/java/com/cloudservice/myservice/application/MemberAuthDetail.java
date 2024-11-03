package com.cloudservice.myservice.application;

import com.cloudservice.myservice.domain.member.Member;

public class MemberAuthDetail {
    private Long id;

    public MemberAuthDetail(Member member) {
        this.id = member.getId();
    }
}
