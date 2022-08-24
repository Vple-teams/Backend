package com.app.vple.service;

import com.app.vple.domain.Cart;
import com.app.vple.domain.User;
import com.app.vple.domain.dto.CartAddDto;
import com.app.vple.domain.dto.CartDetailDto;
import com.app.vple.domain.dto.MyCartDto;
import com.app.vple.repository.CartRepository;
import com.app.vple.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;

    private final UserRepository userRepository;

    public List<MyCartDto> findCart(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NoSuchElementException("해당 사용자가 존재하지 않습니다.")
        );

        List<Cart> cartByUser = cartRepository.findByUser(user);

        if(cartByUser.isEmpty())
            throw new ArrayIndexOutOfBoundsException("장바구니에 담긴 여행지가 없습니다..");

        return cartByUser.stream().map(
                MyCartDto::new
        ).collect(Collectors.toList());
    }

    public CartDetailDto findCartDetails(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 여행지가 존재하지 않습니다.")
        );

        return new CartDetailDto(cart);
    }

    public String addPlan(CartAddDto cartAddDto, String email) {
        try {
            User user = userRepository.findByEmail(email).orElseThrow(
                    () -> new NoSuchElementException("해당 사용자가 존재하지 않습니다."));

            cartRepository.save(cartAddDto.toEntity(user));
            return cartAddDto.getName();
        } catch (Exception e) {
            throw new IllegalStateException("형식이 잘못되었습니다.");
        }
    }

    public String removeCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 여행지가 존재하지 않습니다."));

        cartRepository.delete(cart);

        return cart.getName();
    }
}
