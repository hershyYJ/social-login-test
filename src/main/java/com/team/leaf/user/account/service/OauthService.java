package com.team.leaf.user.account.service;

import com.team.leaf.user.account.dto.OauthAttributes;
import com.team.leaf.user.account.dto.SessionUser;
import com.team.leaf.user.account.entity.User;
import com.team.leaf.user.account.repository.AccountRepository;
import com.team.leaf.user.account.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class OauthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OauthAttributes attributes = OauthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    public SessionUser getSessionUserBySessionId(String sessionId, HttpSession httpSession) {
        Object sessionUserObject = httpSession.getAttribute("user");

        if (sessionUserObject instanceof SessionUser) {
            SessionUser sessionUser = (SessionUser) sessionUserObject;
            return sessionUser;
        }

        return null;
    }

    /*@Transactional
    public SessionUser getSessionUser(OAuth2User oAuth2User, HttpSession httpSession) {
        String registrationId = oAuth2User.getName();
        String userNameAttributeName = oAuth2User.getAttribute("sub");

        OauthAttributes attributes = OauthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new SessionUser(user);
    }*/

    // 유저 생성 및 수정 서비스 로직
    private User saveOrUpdate(OauthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getBirthday(),attributes.getBirthyear(), attributes.getPhone()))
                .orElse(attributes.toEntity());
        return userRepository.save(user);
    }
}
