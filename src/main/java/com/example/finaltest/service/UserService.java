package com.example.finaltest.service;

import com.example.finaltest.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    public User createUser(User user);
    public User findByUsername(String username);
    public User findById(int user_id);
    public void updateUser(User user, int user_id);
    public List<User> findAllUser();
    public void deleteUser(int user_id);
    public List<User> searchUser(String choice, String search);

    public boolean isDuplicatedUsername(String username);
}
