package com.sashkou.SpringSecurityCSRF.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String[] patterns = new String[] {
                "/favicon.ico",
                "/login"
        };

        return http
                .authorizeRequests().antMatchers("/**")
                .permitAll().and().httpBasic().and().formLogin()
                .successHandler(loginSuccessHandler())
                .and()
                .csrf().csrfTokenRepository(csrfTokenRepository())
//                .and().csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("**/login"))
//                .and().csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("**/home"))
//                .and().csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("**/registerEmail"))
                .and()
                .build();
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return new CustomCsrfTokenRepository();
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        //return (request, response, authentication) -> response.sendRedirect("/");
        return (request, response, authentication)-> {
            response.sendRedirect("/home");
        };
    }
}
