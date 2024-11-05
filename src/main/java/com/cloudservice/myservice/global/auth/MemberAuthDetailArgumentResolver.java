package com.cloudservice.myservice.global.auth;

import com.cloudservice.myservice.application.MemberAuthDetail;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class MemberAuthDetailArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return MemberAuthDetail.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {

        MemberAuthDetail detail = MemberAuthDetailHolder.get();
        if (detail == null)
            throw new MemberAuthDetailNotFoundedException();
        return detail;
    }
}
