package com.heshi.audit.config;

import com.heshi.audit.filters.AuditLogInteceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaAuditing
public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    private AuditLogInteceptor auditLogInteceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditLogInteceptor);
    }
}
