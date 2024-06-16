package com.example.finaltest.controller;

import com.example.finaltest.model.Cart;
import com.example.finaltest.model.User;
import com.example.finaltest.repository.CartRepository;
import com.example.finaltest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/login")
    public String login(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Cookie[] cookies = request.getCookies();

        if (session != null && cookies != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                return "redirect:/";
            }
        }
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (request.getSession(false) != null &&  request.getCookies() != null) {
            User loggedInUser = (User) request.getSession(false).getAttribute("user");
            if (loggedInUser != null) {
                if (loggedInUser.getAdmin() == 1) {
                    return "redirect:/admin";
                } else {
                    return "redirect:/";
                }
            }
        }

        User user = userService.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("user", user);

            Cookie cookie = new Cookie("user", username);
            cookie.setMaxAge(60 * 60);
            response.addCookie(cookie);

            if (user.getAdmin() == 1) {
                return "redirect:/admin/books";
            } else {
                return "redirect:/";
            }
        }

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie cookie = new Cookie("user", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Xử lý yêu cầu đăng ký
    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") User user, Model model) {
        try {
            LocalDateTime now = LocalDateTime.now();
            if (user.getCreatedAt() == null) {
                user.setCreatedAt(LocalDateTime.now());
            }
            user.setUpdatedAt(LocalDateTime.now());
            if (user.getUsername().isEmpty() || user.getPassword().isEmpty() || user.getFullname().isEmpty()) {
                return "redirect:/register?error=empty";
            }
            if (userService.findByUsername(user.getUsername()) != null) {
                return "redirect:/register?error=duplicated";
            }
            userService.createUser(user);
            return "redirect:/login?success";
        } catch (Exception e) {
            return "redirect:/register?error";
        }
    }


    @GetMapping("/logged-user")
    public String showUser(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "user-detail";
    }

    @PostMapping("/update-user")
    public String updateUser(Model model, HttpSession session, @ModelAttribute("user") User updateUser) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        updateUser.setCreatedAt(user.getCreatedAt());
        updateUser.setUpdatedAt(LocalDateTime.now());
        session.removeAttribute("user");
        userService.updateUser(updateUser, user.getUserId());
        session.setAttribute("user", updateUser);
        return "redirect:/";
    }

//    @PostMapping("/save/{id}")
//    public String saveUser(@RequestParam("username") String username,
//                           @RequestParam("fullname") String fullname,
//                           @RequestParam("password") String password,
//                           @RequestParam("phone") String phone,
//                           @RequestParam("email") String email,
//                           @RequestParam("createdAt") Timestamp createdAt,
//                           @PathVariable("id") int id){
//        User user = new User(id, username, password, fullname, email, address 0, LocalDateTime.now(), createdAt);
//        userService.updateUser(user, id);
//        return "redirect:/logged-user";
//    }



}
