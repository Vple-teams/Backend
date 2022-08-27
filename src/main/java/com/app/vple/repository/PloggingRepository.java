package com.app.vple.repository;

import com.app.vple.domain.Plogging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PloggingRepository extends JpaRepository<Plogging, Long> {

    Page<Plogging> findAll(Pageable pageable);

    Page<Plogging> findByTitleContaining(String title, Pageable pageable);
}
