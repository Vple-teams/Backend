package com.app.vple.repository;

import com.app.vple.domain.Cart;
import com.app.vple.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUser(User user);
}
