package com.cloudservice.myservice.domain.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KaKaoAccessTokenRes {
    @JsonProperty(value="access_token")
    private String accessToken;
}
