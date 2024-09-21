package com.cloudservice.myservice;

import jakarta.servlet.*;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.UUID;

class TraceIdLoggingFilter implements Filter {
    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        final UUID uuid = UUID.randomUUID();
        MDC.put("traceId", uuid.toString());
        filterChain.doFilter(servletRequest, servletResponse);
        MDC.clear();
    }
}
