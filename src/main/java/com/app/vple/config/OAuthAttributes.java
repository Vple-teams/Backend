package com.app.vple.config;

import com.app.vple.domain.User;
import com.app.vple.domain.enums.Age;
import com.app.vple.domain.enums.Gender;
import com.app.vple.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@Builder
@RequiredArgsConstructor
public class OAuthAttributes {
    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final String name;
    private final String email;
    private final String picture;
    private final String gender;
    private final String age;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        System.out.println("registrationId = " + registrationId);

        if("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }
        else if("google".equals(registrationId)) {
            return ofGoogle(userNameAttributeName, attributes);
        }
        else { // kakao - id
            return ofKakao("id", attributes);
        }
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .gender("null")
                .age("null")
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("nickname"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .gender((String) response.get("gender"))
                .age((String) response.get("age"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        kakaoAccount.put("id", attributes.get("id"));
        kakaoAccount.put("nickname", profile.get("nickname"));
        kakaoAccount.put("profile_image_url", profile.get("profile_image_url"));
        return OAuthAttributes.builder()
                .name((String) kakaoAccount.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .picture((String) kakaoAccount.get("profile_image_url"))
                .gender((String) kakaoAccount.get("gender"))
                .age((String) kakaoAccount.get("age_range"))
                .attributes(kakaoAccount)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        System.out.println("attributes = " + attributes);

        return User.builder()
                .nickname(name)
                .email(email)
                .image(picture)
                .gender(Gender.toGender(gender))
                .age(Age.toAge(age))
                .myRole(Role.MEMBER)
                .build();
    }
}