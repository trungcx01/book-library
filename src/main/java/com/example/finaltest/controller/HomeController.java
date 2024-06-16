package com.example.finaltest.controller;

import com.example.finaltest.model.Book;
import com.example.finaltest.service.BookService;
import com.example.finaltest.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String getBooks(Model model){
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "home";
    }

    @GetMapping("/category/{category}")
    public String bookByCategory(Model model, @PathVariable("category") String category, HttpServletRequest request){
        List<Book> books = bookService.getBooksByCategory(category);
        model.addAttribute("books", books);
        return "home";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam("keyword") String keyword, Model model) {
        List<Book> searchResults = bookService.searchByTitleOrAuthor(keyword);
        model.addAttribute("books", searchResults);
        model.addAttribute("keyword", keyword);
        return "home";
    }

    @GetMapping("/sort")
    public String sortBooks(@RequestParam("sortBy") String sortBy, @RequestParam("category") String category, @RequestParam("keyword") String keyword,  Model model) {
        List<Book> books = new ArrayList<>();
        if (keyword.isEmpty()){
            if(category.isEmpty()){
                books = bookService.getAllBooks();
            }
            else books = bookService.getBooksByCategory(category);
        }
        else{
            books = bookService.searchByTitleOrAuthor(keyword);
        }
        switch (sortBy) {
            case "bestseller_asc":
                // Sắp xếp sách theo bán chạy (tăng dần)
                Collections.sort(books, Comparator.comparing(Book::getSold));
                break;
            case "bestseller_desc":
                // Sắp xếp sách theo bán chạy (giảm dần)
                Collections.sort(books, Comparator.comparing(Book::getSold).reversed());
                break;
            case "price_asc":
                // Sắp xếp sách theo giá bán (tăng dần)
                Collections.sort(books, Comparator.comparing(Book::getPrice));
                break;
            case "price_desc":
                // Sắp xếp sách theo giá bán (giảm dần)
                Collections.sort(books, Comparator.comparing(Book::getPrice).reversed());
                break;
            case "name_asc":
                // Sắp xếp sách theo tên (A-Z)
                Collections.sort(books, Comparator.comparing(Book::getTitle));
                break;
            case "name_desc":
                // Sắp xếp sách theo tên (Z-A)
                Collections.sort(books, Comparator.comparing(Book::getTitle).reversed());
                break;
            default:
        }
        model.addAttribute("books", books);
        model.addAttribute("category", category);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortBy", sortBy);

        return "home::booksFragment";
    }
}
