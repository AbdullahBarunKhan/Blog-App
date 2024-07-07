package com.projects.blogapp.security;

import com.projects.blogapp.users.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

import java.net.http.HttpRequest;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    private JWTAuthenticationFilter jwtAuthenticationFilter;
    private JWTService jwtService;
    private UserService userService;

    public AppSecurityConfig(@Lazy  JWTAuthenticationFilter jwtAuthenticationFilter, JWTService jwtService, UserService userService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Bean
    JWTAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JWTAuthenticationFilter(new JWTAuthenticationManager(jwtService,userService));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

         http.csrf((csrf)->csrf.disable())
                .authorizeHttpRequests(authorizeHttpRequests->authorizeHttpRequests.requestMatchers("/users","/users/login","/articles","/articles/**").permitAll()
                        .anyRequest().authenticated());

         http.addFilterBefore(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class);

         return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/h2-console/**");
    }
}
