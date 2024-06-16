package com.example.finaltest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    @NotEmpty(message = "Title is required")
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @NotEmpty(message = "Author is required")
    @Column(name = "author", nullable = false, length = 255)
    private String author;


    @Column(name = "description", length = 1000)
    private String description;

    //Sử dụng chú thích @DateTimeFormat(pattern = "yyyy-MM-dd") để chỉ định định dạng ngày-tháng tuân theo chuẩn ISO 8601.
    @NotNull(message = "Release Date is required")
    @Column(name = "release_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @Column(name = "pages")
    private int pages;

    @NotEmpty(message = "Category is required")
    @Column(name = "category", nullable = false, length = 255)
    private String category;

    @Column(name = "image_url", length = 1000)
    private String imageUrl;

    @Column(name = "price")
    private long price;
    @Column(name = "sold", nullable = false)
    private int sold;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @OneToMany(mappedBy = "book")
    private List<Review> reviews = new ArrayList<>();

    public Book() {
    }

    public Book(int bookId, String title, String author, String description, LocalDate releaseDate, int pages, String category, String imageUrl, long price, int sold, int stockQuantity, List<Review> reviews) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.description = description;
        this.releaseDate = releaseDate;
        this.pages = pages;
        this.category = category;
        this.imageUrl = imageUrl;
        this.price = price;
        this.sold = sold;
        this.stockQuantity = stockQuantity;
        this.reviews = reviews;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Double getAverageRate() {
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }

        double totalRate = 0.0;
        for (Review review : reviews) {
            totalRate += review.getReviewRate();
        }

        double averageRate = totalRate / (double) reviews.size();
        return Math.round(averageRate * 10.0) / 10.0;
    }


}
