package com.app.vple.service;

import com.app.vple.config.OAuthAttributes;
import com.app.vple.domain.Language;
import com.app.vple.domain.User;
import com.app.vple.domain.UserFollow;
import com.app.vple.domain.dto.NicknameUpdateDto;
import com.app.vple.domain.dto.UserDetailDto;
import com.app.vple.domain.dto.UserUpdateDto;
import com.app.vple.repository.UserFollowRepository;
import com.app.vple.repository.UserLanguageRepository;
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
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserOAuth2Service extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    private final UserFollowRepository userFollowRepository;

    private final UserLanguageRepository userLanguageRepository;

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

    public UserDetailDto findUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("사용자가 존재하지 않습니다."));
        List<UserFollow> followers = userFollowRepository.findByTo(user);
        List<UserFollow> followings = userFollowRepository.findByFrom(user);
        UserDetailDto userDto = new UserDetailDto(user);
        userDto.setFollow(followers, followings);

        return userDto;
    }

    @Transactional
    public void modifyUserNickname(Long id, NicknameUpdateDto updateDto) throws IllegalAccessException {
        User user = userRepository.getById(id);

        if (updateDto.getNickname().length() > 0) {
            Optional<User> checkDuplicatedNickname = userRepository.findByNickname(updateDto.getNickname());

            if (checkDuplicatedNickname.isPresent()) {
                throw new IllegalAccessException("중복된 닉네임입니다.");
            }
        }
        user.updateNickname(updateDto);
        userRepository.save(user);
    }

    @Transactional
    public void modifyUser(Long id, UserUpdateDto updateInfo) {
        User user = userRepository.getById(id);
        user.update(updateInfo);
        userRepository.save(user);

        userLanguageRepository.deleteByUser(user);

        List<String> myLanguages = updateInfo.getLanguages();
        for(int i = 0; i < myLanguages.size(); i++) {
            userLanguageRepository.save(new Language(user, myLanguages.get(i), i));
        }
    }

    @Transactional
    public void modifyUserImage(String url, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("해당 이메일이 없습니다."));

        user.update(url);
        userRepository.save(user);
    }

    @Transactional
    public void modifyAndSaveUserIntroduction(String email, String introduction) {
        User me = userRepository.getByEmail(email);
        me.setIntroduction(introduction);

        userRepository.save(me);
    }

    @Transactional
    public void userLanguagePriority(Long id, String language) {
        User me = userRepository.getById(id);
        Language myLang = userLanguageRepository.findByNameAndUser(language, me)
                .orElseThrow( () -> new NoSuchElementException("no language"));

        Language firstLang = userLanguageRepository.findByPriorityAndUser(0, me)
                .orElseThrow( () -> new NoSuchElementException("no such priority"));
        firstLang.changePR(myLang.getPriority());
        myLang.changePR(0);

        userLanguageRepository.save(firstLang);
        userLanguageRepository.save(myLang);
    }
}
