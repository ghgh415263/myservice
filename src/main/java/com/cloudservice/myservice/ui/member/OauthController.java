package com.cloudservice.myservice.ui.member;

import com.cloudservice.myservice.global.auth.Token;
import com.cloudservice.myservice.global.auth.TokenManager;
import com.cloudservice.myservice.application.OauthService;
import io.micrometer.core.annotation.Counted;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OauthController {

    private final OauthService oauthService;

    private final TokenManager tokenManager;

    @Counted("oauth.login")
    @GetMapping("/login/oauth2/code/kakao")
    public String kakaoCallback(@RequestParam String code, HttpServletResponse response){

        Long loginMemberId = oauthService.login(code);

        Token token = tokenManager.generateToken(loginMemberId);

        Cookie cookie = new Cookie("Authorization", token.getAccessToken());
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge((24*60*60)-5);

        response.addCookie(cookie);

        return "redirect:/";
    }
}