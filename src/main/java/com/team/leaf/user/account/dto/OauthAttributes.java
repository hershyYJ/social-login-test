package com.team.leaf.user.account.dto;

import lombok.Builder;
import lombok.Getter;
import com.team.leaf.user.account.entity.User;
import com.team.leaf.user.account.entity.AccountRole;

import java.util.Map;

@Getter
public class OauthAttributes {
    private Map<String, Object> attributes; // OAuth2 반환하는 유저 정보 Map
    private String nameAttributeKey;
    private String name;
    private String email;
    private String birthday;
    private String birthyear;
    private String phone;

    @Builder
    public OauthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String birthday, String birthyear, String phone) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.birthyear = birthyear;
        this.phone = phone;
    }

    public static OauthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        if("kakao".equals(registrationId)){
            return ofKakao("id", attributes);
        }
        if("naver".equals(registrationId)){
            return ofNaver("id", attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OauthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");

        return OauthAttributes.builder()
                .name((String) kakaoAccount.get("name"))
                .email((String) kakaoAccount.get("email"))
                .birthday((String) kakaoAccount.get("birthday"))
                .birthyear((String) kakaoAccount.get("birthyear"))
                .phone((String) kakaoAccount.get("phone_number"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OauthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>)attributes.get("response");

        return OauthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .birthday((String) response.get("birthday"))
                .birthyear((String) response.get("birthyear"))
                .phone((String) response.get("mobile"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OauthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OauthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .birthday(birthday)
                .birthyear(birthyear)
                .phone(phone)
                .role(AccountRole.USER) // 기본 권한 GUEST
                .build();
    }

}