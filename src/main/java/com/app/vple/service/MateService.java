package com.app.vple.service;

import com.app.vple.domain.Mate;
import com.app.vple.domain.User;
import com.app.vple.domain.dto.MateInfoDto;
import com.app.vple.repository.MateRepository;
import com.app.vple.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MateService {

    private final MateRepository mateRepository;

    private final UserRepository userRepository;

    private final GeoCodingService geoCodingService;

    @Transactional
    public void addMate(String email, String latitude, String longitude) throws IOException {
        User me = userRepository.findByEmail(email)
                .orElseThrow( () -> new NoSuchElementException("유저가 존재하지 않습니다."));

        List<String> address = geoCodingService.findHere(longitude, latitude);

        if (address.size() < 2) {
            throw new NoSuchElementException("주소를 찾을 수 없습니다.");
        }

        Mate mate = Mate.builder()
                .user(me)
                .longitude(longitude)
                .latitude(latitude)
                .nickname(me.getNickname())
                .cityDistrict(address.get(0))
                .dong(address.get(1))
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

        return mateRepository.findAllByCityDistrictAndDongAndNicknameNot(myMate.getCityDistrict(), myMate.getDong(), me.getNickname()).stream()
                .map(MateInfoDto::new).collect(Collectors.toList());
    }
}
