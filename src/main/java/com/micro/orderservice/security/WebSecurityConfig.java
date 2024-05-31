package com.micro.orderservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)

public class WebSecurityConfig {
    
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http)throws Exception{
        http
            .authorizeHttpRequests(authorizeRequests -> 
                authorizeRequests.anyRequest().authenticated())
            .oauth2ResourceServer(oauth2 -> 
                oauth2.jwt(jwtConfigurer -> {})
            );

            return http.build();

    }
}
