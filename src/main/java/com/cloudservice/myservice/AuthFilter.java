package com.cloudservice.myservice;

import com.cloudservice.myservice.application.MemberAuthDetail;
import com.cloudservice.myservice.application.MemberAuthDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@Order(2)
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenProvider tokenProvider;
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
            Cookie[] cookies = request.getCookies();
            if (cookies == null){
                filterChain.doFilter(request, response);
                return;
            }

            Cookie cookie = Arrays.stream(cookies)
                    .filter(c -> c.getName().equals(AUTHORIZATION_HEADER))
                    .findAny()
                    .orElse(null);

            if (cookie != null) {
                String token = cookie.getValue();
                MemberAuthDetail memberAuthDetail = memberAuthDetailService.getMemberAuthDetail(tokenProvider.getMemberId(token));
                MemberAuthDetailHolder.set(memberAuthDetail);
            }

            filterChain.doFilter(request, response);
        }
        finally {
            MemberAuthDetailHolder.clear();
        }
    }
}
