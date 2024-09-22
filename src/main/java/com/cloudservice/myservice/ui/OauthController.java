package com.cloudservice.myservice.ui;

import com.cloudservice.myservice.application.OauthService;
import io.micrometer.core.annotation.Counted;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequiredArgsConstructor
public class OauthController {

    private final OauthService oauthService;

    @Counted("oauth.login")
    @GetMapping("/login/oauth2/code/kakao")
    public String kakaoCallback(@RequestParam String code, HttpServletRequest httpServletRequest){

        Long loginMemberId = oauthService.login(code);

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("memberId", loginMemberId);

        return "login성공" + loginMemberId;
    }
}