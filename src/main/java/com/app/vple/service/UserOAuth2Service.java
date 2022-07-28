package com.app.vple.service;

import com.app.vple.config.OAuthAttributes;
import com.app.vple.domain.User;
import com.app.vple.domain.dto.UserDetailDto;
import com.app.vple.domain.dto.UserUpdateDto;
import com.app.vple.repository.UserRepository;
import com.app.vple.service.model.SessionLoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserOAuth2Service extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // naver, kakao 로그인 구분
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionLoginUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleValue())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture(), attributes.getAge()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }

    public UserDetailDto findUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NoSuchElementException("이메일이 존재하지 않습니다."));
        return new UserDetailDto(user);
    }

    @Transactional
    public User modifyUser(String email, UserUpdateDto updateInfo) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("해당 이메일이 없습니다."));

        if (updateInfo.getNickname().equals(user.getNickname())) {
            throw new IllegalAccessException("변경 사항이 없습니다.");
        }

        else if (updateInfo.getNickname().length() > 0) {
            Optional<User> checkDuplicatedNickname = userRepository.findByNickname(updateInfo.getNickname());

            if (checkDuplicatedNickname.isPresent()) {
                throw new IllegalAccessException("중복된 닉네임입니다.");
            }
        }

        user.update(updateInfo);

        return userRepository.save(user);
    }
}
