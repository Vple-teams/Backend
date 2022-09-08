package com.app.vple.service;

import com.app.vple.domain.Mate;
import com.app.vple.domain.User;
import com.app.vple.domain.dto.MateInfoDto;
import com.app.vple.repository.MateRepository;
import com.app.vple.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MateService {

    private final MateRepository mateRepository;

    private final UserRepository userRepository;


    @Transactional
    public void addMate(String email, Double latitude, Double longitude) throws Exception {
        User me = userRepository.findByEmail(email)
                .orElseThrow( () -> new NoSuchElementException("유저가 존재하지 않습니다."));

        if(mateRepository.existsByUser(me)) {
            throw new Exception("이미 메이트기능을 사용중입니다.");
        }

        Mate mate = Mate.builder()
                .user(me)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        mateRepository.save(mate);
    }

    @Transactional
    public void deleteMate(String email) {
        User me = userRepository.findByEmail(email)
                .orElseThrow( () -> new NoSuchElementException("유저가 존재하지 않습니다."));

        Mate myMate = mateRepository.findByUser(me)
                .orElseThrow( () -> new NoSuchElementException("mate 기능이 활성화되어 있지 않습니다."));

        mateRepository.delete(myMate);
    }

    public List<MateInfoDto> findMate(String email) {
        User me = userRepository.findByEmail(email)
                .orElseThrow( () -> new NoSuchElementException("유저가 존재하지 않습니다."));

        Mate myMate = mateRepository.findByUser(me)
                .orElseThrow( () -> new NoSuchElementException("mate 기능이 활성화되어 있지 않습니다."));

        Double myLong = myMate.getLongitude();
        Double myLat  = myMate.getLatitude();

        List<Mate> mates = mateRepository.findAllNearByMe(myLong, myLat, me.getId());
        return mates.stream().map(MateInfoDto::new).collect(Collectors.toList());
    }
}
