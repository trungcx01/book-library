package com.example.finaltest.service;

import com.example.finaltest.model.Cart;

import java.util.List;

public interface CartService {
    List<Cart> getCartsByUser(int userId);
    void deleteCartById(int cartId);
}
