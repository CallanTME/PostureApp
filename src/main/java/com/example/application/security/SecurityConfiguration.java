package com.example.application.security;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/* 
Reference 1 - Taken from https://vaadin.com/learn/tutorials/modern-web-apps-with-spring-boot-and-vaadin/adding-a-login-screen-to-a-vaadin-app-with-spring-security

This code is slightly edited but the bulk is from the tutorial
*/

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?error";
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_SUCCESS_URL = "/login";


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .requestCache().requestCache(new CustomRequestCache())
                .and().authorizeRequests()
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

                .anyRequest().authenticated()

                .and().formLogin()
                .loginPage(LOGIN_URL).permitAll()
                .loginProcessingUrl(LOGIN_PROCESSING_URL)
                .failureUrl(LOGIN_FAILURE_URL)
                .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
    }

    @Bean
    @Override
    // Defines the user log in details
    public UserDetailsService userDetailsService() {

        //Defines all the users who can access the app
        UserDetails user1 =
                User.withUsername("Callan")
                        .password("{noop}posture1")
                        .roles("USER")
                        .build();

        UserDetails user2 =
                User.withUsername("Olly")
                        .password("{noop}posture1")
                        .roles("USER")
                        .build();

        UserDetails user3 =
                User.withUsername("Nikita")
                        .password("{noop}posture1")
                        .roles("USER")
                        .build();

        UserDetails user4 =
                User.withUsername("Alice")
                        .password("{noop}posture1")
                        .roles("USER")
                        .build();

        UserDetails user5 =
                User.withUsername("MrHolloway")
                        .password("{noop}nettles")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user1, user2, user3, user4, user5);

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/VAADIN/**",
                "/favicon.ico",
                "/robots.txt",
                "/manifest.webmanifest",
                "/sw.js",
                "/offline.html",
                "/icons/**",
                "/images/**",
                "/styles/**",
                "/h2-console/**");
    }
}


