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
                default -> null;
            };
        }

        private ClientRegistration googleClientRegistration() {
            return ClientRegistration.withRegistrationId("google")
                    .clientId("clientId")
                    .clientSecret("clientSecret")
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .redirectUri("redirectURi")
                    .scope("https://www.googleapis.com/auth/userinfo.email",
                            "https://www.googleaprmsis.com/auth/userinfo.profile",
                            "https://www.googleapis.com/auth/user.birthday.read",
                            "https://www.googleapis.com/auth/user.phonenumbers.read")
                    .build();
        }
    }
}

