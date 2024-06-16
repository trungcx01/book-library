package com.example.finaltest.service;

import com.example.finaltest.model.Review;

public interface ReviewService {
    public Review createReview(Review review);
    public void deleteReview(int reviewId);
    public void updateReview(Review updateReview);
}
