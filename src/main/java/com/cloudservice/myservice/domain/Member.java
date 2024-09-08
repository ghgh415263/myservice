package com.cloudservice.myservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private LoginProviderType loginProviderType;

    private String loginId;

    private String password;

    @Column(unique = true)
    private String oauthId;

    @Builder
    public Member(LoginProviderType loginProviderType, String loginId, String password, String oauthId) {
        this.loginProviderType = loginProviderType;
        this.loginId = loginId;
        this.password = password;
        this.oauthId = oauthId;
    }
}
