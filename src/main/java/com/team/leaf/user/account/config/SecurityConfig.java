package com.team.leaf.user.account.config;

import com.team.leaf.user.account.service.OauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final OauthService oauthService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers("/login/**", "/login/oauth2/code/**", "/oauth/**").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
