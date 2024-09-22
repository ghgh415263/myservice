package com.cloudservice.myservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseUpdateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoginProviderType loginProviderType;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
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
