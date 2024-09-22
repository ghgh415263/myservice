package com.cloudservice.myservice.ui;

import com.cloudservice.myservice.application.MemberSaveReq;
import com.cloudservice.myservice.application.MemberSaveService;
import com.cloudservice.myservice.domain.WeakPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/members/create")
public class JoinController {

    private final MemberSaveService memberSaveService;

    @GetMapping
    public String joinPage(@ModelAttribute MemberSaveReq memberSaveReq) {
        return "member/joinForm";
    }

    @PostMapping
    public String join(@ModelAttribute @Validated MemberSaveReq memberSaveReq,
                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "member/joinForm";
        }

        try {
            memberSaveService.saveMember(memberSaveReq);
        }
        catch(WeakPasswordException e){
            bindingResult.rejectValue("password",null, e.getMessage());
            return "member/joinForm";
        }

        return "member/joinComplete";
    }
}
