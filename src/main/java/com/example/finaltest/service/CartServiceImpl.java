package com.example.finaltest.service;

import com.example.finaltest.model.Cart;
import com.example.finaltest.model.User;
import com.example.finaltest.repository.BookRepository;
import com.example.finaltest.repository.CartRepository;
import com.example.finaltest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Cart> getCartsByUser(int userId) {
        User user = userService.findById(userId);
        return cartRepository.findByUser(user);
    }

    @Override
    public void deleteCartById(int cartId) {
        cartRepository.deleteById(cartId);
    }
}
