package com.example.finaltest.repository;

import com.example.finaltest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
//    boolean existsByTitleAndAuthorAndBookIdNot(String title, String author, int bookId);
}
