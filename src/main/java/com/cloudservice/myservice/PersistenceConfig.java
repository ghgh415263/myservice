package com.cloudservice.myservice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

@Configuration
public class PersistenceConfig {

    @Bean
    public AuditorAware<Long> auditorAware() {
        return new AuditorAwareImpl();
    }

    static class AuditorAwareImpl implements AuditorAware<Long>, DateTimeProvider {
        @Override
        public Optional<Long> getCurrentAuditor() {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            HttpSession session = request.getSession();
            Long id = (Long) session.getAttribute("memberId");
            return Optional.ofNullable( id==null? 0 : id);
        }

        @Override
        public Optional<TemporalAccessor> getNow() {
            return Optional.of(ZonedDateTime.now());
        }
    }
}
