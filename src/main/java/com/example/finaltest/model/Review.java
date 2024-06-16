package com.example.finaltest.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "comment")
    private String comment;

    @Column(name = "review_date")
    private LocalDateTime reviewDate;

    @Column(name = "review_rate")
    private int reviewRate;

    public Review() {
    }

    public Review(Book book, User user, String comment, LocalDateTime reviewDate, int reviewRate) {
        this.book = book;
        this.user = user;
        this.comment = comment;
        this.reviewDate = reviewDate;
        this.reviewRate = reviewRate;
    }

    public Review(int reviewId, Book book, User user, String comment, LocalDateTime reviewDate, int reviewRate) {
        this.reviewId = reviewId;
        this.book = book;
        this.user = user;
        this.comment = comment;
        this.reviewDate = reviewDate;
        this.reviewRate = reviewRate;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    public int getReviewRate() {
        return reviewRate;
    }

    public void setReviewRate(int reviewRate) {
        this.reviewRate = reviewRate;
    }
}
