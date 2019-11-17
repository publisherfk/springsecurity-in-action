package com.heshi.acl.config;

import com.heshi.acl.filters.AclInteceptor;
import com.heshi.audit.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
@EnableJpaAuditing
public class AclSecurityConfig extends SecurityConfig {
    @Autowired
    private AclInteceptor aclInteceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(aclInteceptor);
    }
}
