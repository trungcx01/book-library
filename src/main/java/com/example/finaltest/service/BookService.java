package com.example.finaltest.service;

import com.example.finaltest.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Page<Book> findAllBooksByPage(int page, int size);
    Book getBookById(int book_id);
    void deleteBookById(int book_id);
    void createNewBook(Book book);
    void updateBook(Book updateBook);
    List<Book> getBooksByCategory(String category);
    boolean isDuplicated(String title, String author);
    boolean isDuplicatedAndNotId(String title, String author, int id);

    List<Book> searchByTitleOrAuthor(String keyword);
}
