package com.mugon.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login", "/register", "/register/idCheck", "/register/checkEmail","/js/**", "/css/**", "/images/**", "/fonts/**", "/reply", "/reply/idCheck").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .usernameParameter("id")
                .loginPage("/login")
                .successForwardUrl("/loginSuccess")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll();

        http.csrf().disable();
    }
}