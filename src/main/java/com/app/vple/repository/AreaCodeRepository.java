package com.app.vple.repository;

import com.app.vple.domain.AreaCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaCodeRepository extends JpaRepository<AreaCode, Long> {

    AreaCode getByCityAndDistrict(String city, String district);

    List<AreaCode> findByCity(String city);
}
