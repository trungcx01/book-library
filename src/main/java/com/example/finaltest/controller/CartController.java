package com.example.finaltest.controller;

import com.example.finaltest.model.*;
import com.example.finaltest.repository.BookRepository;
import com.example.finaltest.repository.CartItemRepository;
import com.example.finaltest.repository.CartRepository;
import com.example.finaltest.service.BookService;
import com.example.finaltest.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/delete-cart/{cartId}")
    public String deleteCart(@PathVariable("cartId") int id, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        cartService.deleteCartById(id);
        return "redirect:/cart-history";
    }

    @GetMapping("/cart")
    public String formCart(Model model){
        model.addAttribute("cart", new Cart());
        return "cart";
    }

    @PostMapping("/create-cart")
    public String createNewCart(Model model, HttpSession session,
                                @RequestParam("title") List<String> title,
                                @RequestParam("quantity") List<Long> quantity,
                                @RequestParam("total") List<Long> total,
                                @RequestParam("shippingAddress") String shippingAddress,
                                @RequestParam("description") String description) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // Xử lý khi người dùng chưa đăng nhập
            return "redirect:/login"; // Hoặc trang đăng nhập tùy chọn
        }

        Cart cart = new Cart();
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUser(user);
        cart.setShippingAddress(shippingAddress);
        cart.setDescription(description);

        long totalPrice = 0;

        for (int i = 0; i < title.size(); i++) {
            Book book = bookRepository.findByTitle(title.get(i));
            if (book != null) {
                CartItem cartItem = new CartItem();
                cartItem.setBook(book);
                cartItem.setQuantity(quantity.get(i));
                cartItem.setPrice(total.get(i));
                cartItem.setCart(cart);
                cart.getCartItems().add(cartItem);

                totalPrice += total.get(i);
            }
        }

        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);
        for (CartItem cartItem : cart.getCartItems()){
            cartItemRepository.save(cartItem);
        }

        return "redirect:/cart-history";
    }

    @GetMapping("/cart-history")
    public String getCartByUser(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("carts", cartService.getCartsByUser(user.getUserId()));
        return "cart-history";
    }


}
