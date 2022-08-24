package com.app.vple.repository;

import com.app.vple.domain.Mate;
import com.app.vple.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MateRepository extends JpaRepository<Mate, Long> {

    Optional<Mate> findByUser(User user);

    List<Mate> findAllByCityDistrictAndDongAndNicknameNot(String cityDistrict, String dong, String me);
}
