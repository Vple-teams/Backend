package com.app.vple.repository;

import com.app.vple.domain.AreaCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AreaCodeRepository extends JpaRepository<AreaCode, Long> {

    AreaCode getByCityAndDistrict(String city, String district);

    List<AreaCode> findByCity(String city);

    AreaCode getByCityContaining(String city);

    List<AreaCode> findByDistrictContaining(String district);

    AreaCode getByDistrictContaining(String district);
}
