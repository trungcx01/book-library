package com.example.finaltest.service;

import com.example.finaltest.model.Review;
import com.example.finaltest.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService{
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(int reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public void updateReview(Review updateReview) {
        Review review = reviewRepository.findById(updateReview.getReviewId()).orElse(null);
        if (review != null){
            review.setReviewRate(updateReview.getReviewRate());
            review.setBook(updateReview.getBook());
            review.setComment(updateReview.getComment());
            review.setUser(updateReview.getUser());
            review.setReviewDate(updateReview.getReviewDate());
            reviewRepository.save(review);
        }
    }
}
