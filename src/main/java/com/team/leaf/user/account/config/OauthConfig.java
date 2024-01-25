package com.team.leaf.user.account.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;


@Configuration
public class OauthConfig {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new CustomClientRegistrationRepository();
    }

    private static class CustomClientRegistrationRepository implements ClientRegistrationRepository {

        @Override
        public ClientRegistration findByRegistrationId(String registrationId) {
            return switch (registrationId) {
                case "google" -> googleClientRegistration();
                case "naver" -> naverClientRegistration();
                case "kakao" -> kakaoClientRegistration();
                default -> null;
            };
        }

        private ClientRegistration googleClientRegistration() {
            return ClientRegistration.withRegistrationId("google")
                    .clientId("client_id")
                    .clientSecret("client_secret")
                    //.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    //.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .redirectUri("http://localhost:8080/login/oauth2/code/google")
                    .scope("https://www.googleapis.com/auth/userinfo.email",
                            "https://www.googleaprmsis.com/auth/userinfo.profile")
                    .build();
        }

        private ClientRegistration naverClientRegistration() {
            return ClientRegistration.withRegistrationId("naver")
                    .clientId("client_id")
                    .clientSecret("client_secret")
                    //.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    //.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .redirectUri("http://localhost:8080/login/oauth2/code/naver")
                    .build();
        }

        private ClientRegistration kakaoClientRegistration() {
            return ClientRegistration.withRegistrationId("kakao")
                    .clientId("client_id")
                    .clientSecret("client_secret")
                    //.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .redirectUri("http://localhost:8080/login/oauth2/code/kakao")
                    .build();
        }
    }
}

