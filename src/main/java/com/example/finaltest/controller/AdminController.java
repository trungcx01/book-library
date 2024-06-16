package com.example.finaltest.controller;

import com.example.finaltest.model.Book;
import com.example.finaltest.model.User;
import com.example.finaltest.service.BookService;
import com.example.finaltest.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Controller
public class AdminController {
    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @GetMapping("/admin/books")
    public String getBooks(Model model, HttpSession session,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "10") int size) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }
        Page<Book> bookPage = bookService.findAllBooksByPage(page, size);
        List<Book> books = bookPage.getContent();

        model.addAttribute("books", books);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookPage.getTotalPages());

        return "books";
    }

    @GetMapping("/admin/book/{id}")
    public String getBookById(Model model, @PathVariable("id") int id){
        Book book = bookService.getBookById(id);
        if (book == null){
            book = new Book();
        }
        model.addAttribute("book", book);
        return "book-detail";
    }

    @PostMapping("/admin/book/save/{id}")
    public String createNewBook(Model model, @Validated @ModelAttribute("book") Book book, BindingResult bindingResult,  @RequestParam("image") MultipartFile imageFile) {
        if (bookService.isDuplicated(book.getTitle(), book.getAuthor())) {
            bindingResult.rejectValue("title", "error.book", "Error at field Title: Book with the same title and author already exists");
            bindingResult.rejectValue("author", "error.book", "Error at field Author: Book with the same title and author already exists");
        }

        if (!imageFile.isEmpty() && imageFile != null){
            Path path = Paths.get("uploads/");
            try{
                InputStream inputStream = imageFile.getInputStream();
                Files.copy(inputStream, path.resolve(imageFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                book.setImageUrl(imageFile.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("book", new Book());
            return "book-detail";
        }
        bookService.createNewBook(book);
        return "redirect:/admin/books?success=added";
    }

    @PutMapping("/admin/book/save/{id}")
    public String updateBook(Model model, @Validated @ModelAttribute("book") Book updateBook, BindingResult bindingResult,  @RequestParam("image") MultipartFile imageFile, @RequestParam("empty") String empty,  @PathVariable("id") int id) {
        if (bookService.isDuplicatedAndNotId(updateBook.getTitle(), updateBook.getAuthor(), id)) {
            bindingResult.rejectValue("title", "error.book", "Error at field Title: Book with the same title and author already exists");
            bindingResult.rejectValue("author", "error.book", "Error at field Author: Book with the same title and author already exists");
        }
        if (!imageFile.isEmpty()){
            Path path = Paths.get("uploads/");
            try {
                InputStream inputStream = imageFile.getInputStream();
                Files.copy(inputStream, path.resolve(imageFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                updateBook.setImageUrl(imageFile.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
           if (empty.equals("empty")){
               updateBook.setImageUrl(null);
           }
           else updateBook.setImageUrl(bookService.getBookById(id).getImageUrl());
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("book", bookService.getBookById(id));
            return "book-detail";
        }
        bookService.updateBook(updateBook);
        return "redirect:/admin/books";
    }

    @GetMapping("/admin/book/delete/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookService.deleteBookById(id);
        return "redirect:/admin/books";
    }

    @GetMapping("/admin/search")
    public String searchBooksByAdmin(@RequestParam("keyword") String keyword, Model model) {
        List<Book> searchResults = bookService.searchByTitleOrAuthor(keyword);
        model.addAttribute("books", searchResults);
        model.addAttribute("keyword", keyword);
        return "books";
    }
}
