package com.sitesstorageproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationEntryPoint entryPoint,
                                           AuthorizationManager<RequestAuthorizationContext> manager) throws Exception {
        http
                .formLogin((form) -> form
                        .loginPage("/login")
//                        .loginProcessingUrl("/login")
//                        .usernameParameter("username")
//                        .passwordParameter("password")
//                        .failureUrl("/login?error=true")
//                        .defaultSuccessUrl("/")
                        .permitAll());
//                .httpBasic(withDefaults());
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/index.html", "/register", "/public-resources/**", "/about", "/error").permitAll()
                        .requestMatchers("/site/**").access(manager)
                        .anyRequest().authenticated()).httpBasic().authenticationEntryPoint(entryPoint);

        http.logout().logoutSuccessUrl("/").logoutUrl("/logout").permitAll();
        http.csrf().disable();
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, CustomAuthenticationProvider authenticationProvider) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).authenticationProvider(authenticationProvider).build();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder(13);
    }
}
