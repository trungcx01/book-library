package com.example.finaltest.repository;

import com.example.finaltest.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    boolean existsByTitleAndAuthor(String title, String author);
    boolean existsByTitleAndAuthorAndBookIdNot(String title, String author, int bookId);
    Book findByTitle(String title);
    List<Book> findByCategory(String category);

    @Query("SELECT b FROM Book b WHERE b.title LIKE %:keyword% OR b.author LIKE %:keyword%")
    List<Book> searchByTitleOrAuthor(@Param("keyword") String keyword);
}
