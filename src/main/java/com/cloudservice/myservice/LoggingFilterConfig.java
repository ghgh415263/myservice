package com.cloudservice.myservice;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.UUID;

@Configuration
public class LoggingFilterConfig {
    @Bean
    public FilterRegistrationBean<Filter> mDCLoggingFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new
                FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new TraceIdLoggingFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    static class TraceIdLoggingFilter implements Filter {
        @Override
        public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
            final UUID uuid = UUID.randomUUID();
            MDC.put("traceId", uuid.toString());
            filterChain.doFilter(servletRequest, servletResponse);
            MDC.clear();
        }
    }
}
