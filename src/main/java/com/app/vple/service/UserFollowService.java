package com.app.vple.service;

import com.app.vple.domain.User;
import com.app.vple.domain.UserFollow;
import com.app.vple.exception.NoSuchUserException;
import com.app.vple.repository.UserFollowRepository;
import com.app.vple.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFollowService {

    private final UserFollowRepository userFollowRepository;

    private final UserRepository userRepository;

    @Transactional
    public String followUser(Long toUserId, String email) throws NoSuchUserException {
        User me = userRepository.findByEmail(email).orElseThrow(
                () -> new NoSuchUserException("유저가 없습니다.")
        );

        User to = userRepository.findById(toUserId).orElseThrow(
                () -> new NoSuchUserException("유저가 없습니다.")
        );

        UserFollow userFollow = UserFollow.builder()
                .from(me)
                .to(to)
                .build();

        userFollowRepository.save(userFollow);

        return to.getNickname();
    }

    @Transactional
    public String unFollowUser(Long toUserId, String email) throws NoSuchUserException {
        User me = userRepository.findByEmail(email).orElseThrow(
                () -> new NoSuchUserException("유저가 없습니다.")
        );

        User to = userRepository.findById(toUserId).orElseThrow(
                () -> new NoSuchUserException("유저가 없습니다.")
        );

        UserFollow follow = userFollowRepository.getByFromAndTo(me, to);
        userFollowRepository.delete(follow);

        return to.getNickname();
    }
}
