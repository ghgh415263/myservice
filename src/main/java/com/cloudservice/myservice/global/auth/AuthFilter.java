package com.cloudservice.myservice.global.auth;

import com.cloudservice.myservice.application.MemberAuthDetail;
import com.cloudservice.myservice.application.MemberAuthDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
@Order(2)
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenManager tokenManager;
    private final MemberAuthDetailService memberAuthDetailService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String[] excludePath = {"/favicon.ico"};
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            Cookie cookie = getAuthCookie(request);
            if (cookie != null) {
                String token = cookie.getValue();
                MemberAuthDetail memberAuthDetail = memberAuthDetailService.getMemberAuthDetail(tokenManager.getMemberId(token));
                MemberAuthDetailHolder.set(memberAuthDetail);
            }

            filterChain.doFilter(request, response);
        }
        catch (InValidTokenException e) {
            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            filterChain.doFilter(request, response);
        }
        finally {
            MemberAuthDetailHolder.clear();
        }
    }

    private Cookie getAuthCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return null;

        return Arrays.stream(cookies)
                .filter(c -> c.getName().equals(AUTHORIZATION_HEADER))
                .findAny()
                .orElse(null);
    }
}
