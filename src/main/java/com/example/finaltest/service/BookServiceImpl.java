package com.example.finaltest.service;

import com.example.finaltest.model.Book;
import com.example.finaltest.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Page<Book> findAllBooksByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findAll(pageable);
    }

    @Override
    public Book getBookById(int book_id) {
        return bookRepository.findById(book_id).orElse(null);
    }

    @Override
    public void deleteBookById(int book_id) {
        bookRepository.deleteById(book_id);
    }

    @Override
    public void createNewBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void updateBook(Book updateBook) {
        Book existingBook = bookRepository.findById(updateBook.getBookId()).orElse(null);
        if (existingBook != null){
            existingBook.setAuthor(updateBook.getAuthor());
            existingBook.setCategory(updateBook.getCategory());
            existingBook.setDescription(updateBook.getDescription());
            existingBook.setPages(updateBook.getPages());
            existingBook.setReleaseDate(updateBook.getReleaseDate());
            existingBook.setPrice(updateBook.getPrice());
            existingBook.setImageUrl(updateBook.getImageUrl());
            existingBook.setSold(updateBook.getSold());
            existingBook.setTitle(updateBook.getTitle());
            existingBook.setStockQuantity(updateBook.getStockQuantity());
            bookRepository.save(existingBook);
        }
    }

    @Override
    public List<Book> getBooksByCategory(String category) {
        return bookRepository.findByCategory(category);
    }


    @Override
    public boolean isDuplicated(String title, String author) {
        return bookRepository.existsByTitleAndAuthor(title, author);
    }

    @Override
    public boolean isDuplicatedAndNotId(String title, String author, int id) {
        return bookRepository.existsByTitleAndAuthorAndBookIdNot(title, author, id);
    }

    @Override
    public List<Book> searchByTitleOrAuthor(String keyword) {
        return bookRepository.searchByTitleOrAuthor(keyword);
    }


}
