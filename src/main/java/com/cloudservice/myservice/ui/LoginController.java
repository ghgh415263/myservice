package com.cloudservice.myservice.ui;

import com.cloudservice.myservice.application.MemberLoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberLoginService memberLoginService;

    @GetMapping("/login")
    public String getLoginForm(@ModelAttribute LoginForm form){
        return "/member/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm form,
                        BindingResult bindingResult,
                        HttpServletRequest httpServletRequest,
                        @RequestParam(defaultValue = "/") String redirectURL){

        if (bindingResult.hasErrors())
            return "/member/loginForm";

        Long loginMemberId = memberLoginService.login(form.getLoginId(), form.getPassword());

        if (loginMemberId == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "/member/loginForm";
        }

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("memberId", loginMemberId);

        return "redirect:" + redirectURL;
    }

    @Getter
    @AllArgsConstructor
    static class LoginForm {

        @NotBlank
        private String loginId;

        @NotBlank
        private String password;
    }

}
