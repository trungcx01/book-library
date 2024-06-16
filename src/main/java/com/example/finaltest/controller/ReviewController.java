package com.example.finaltest.controller;

import com.example.finaltest.model.Book;
import com.example.finaltest.model.Review;
import com.example.finaltest.model.User;
import com.example.finaltest.service.BookService;
import com.example.finaltest.service.ReviewService;
import com.example.finaltest.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;


    @PostMapping("/book/{id}/new-comment") // Thêm dấu / sau {id}
    public String createNewReview(@PathVariable int id, HttpServletRequest request,
                                  @RequestParam("reviewRate") int reviewRate,
                                  @RequestParam("comment") String comment) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        Book book = bookService.getBookById(id);
        reviewService.createReview(new Review(book, user, comment, LocalDateTime.now(), reviewRate));
        return "redirect:/book/{id}";
    }

//    @PutMapping("/book/{id}/update/{reviewId}")
//    public String updateReview(@PathVariable("id") int id, @PathVariable("reviewId") int reviewId,
//                               HttpServletRequest request,
//                               @RequestParam("reviewRate") int reviewRate,
//                               @RequestParam("comment") String comment){
//        User user = (User) request.getSession().getAttribute("user");
//        Book book = bookService.getBookById(id);
//        Review updateReview = new Review(reviewId, book, user, comment, LocalDateTime.now(), reviewRate);
//        reviewService.updateReview(updateReview);
//        return "redirect:/book/{id}";
//    }
    @GetMapping("/book/{id}/delete-review/{reviewId}")
    public String deleteReview(@PathVariable int id, @PathVariable int reviewId) {
        reviewService.deleteReview(reviewId);
        return "redirect:/book/{id}";
    }



}
