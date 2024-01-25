package com.team.leaf.user.account.config;

import com.team.leaf.user.account.service.OauthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final OauthService oauthService;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    public SecurityConfig(OauthService oauthService, AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.oauthService = oauthService;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                //.formLogin(formLogin -> formLogin.disable())

                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers("/login/**", "/login/oauth2/code/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .userInfoEndpoint(userInfoEndpoint ->
                                userInfoEndpoint.userService(oauthService))
                                .successHandler(authenticationSuccessHandler)
                );

        return http.build();
    }
}
