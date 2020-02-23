package com.heshi.customlogin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author by fukun
 */
@Configuration
@EnableWebSecurity
public class CustomLoginConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/oauth/login", "/oauth/loginCheck", "/oauth/logout", "/css/**", "/code/image")
                .permitAll().anyRequest().authenticated().and().formLogin().loginPage("/oauth/login")
                .loginProcessingUrl("/oauth/loginCheck").and().logout().logoutUrl("/oauth/logout").logoutSuccessUrl("/oauth/login").invalidateHttpSession(true).and().csrf().disable();
    }
}
