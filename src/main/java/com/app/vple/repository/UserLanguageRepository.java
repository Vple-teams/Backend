package com.app.vple.repository;

import com.app.vple.domain.Language;
import com.app.vple.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLanguageRepository extends JpaRepository<Language, Long> {

    void deleteByUser(User user);

    Optional<Language> findByPriorityAndUser(Integer priority, User user);

    Optional<Language> findByNameAndUser(String name, User user);
}
