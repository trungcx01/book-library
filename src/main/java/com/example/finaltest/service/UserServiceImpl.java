package com.example.finaltest.service;

import com.example.finaltest.model.User;
import com.example.finaltest.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User createUser(User user) {
        user.setPassword(user.getPassword());
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(int user_id) {
        return userRepository.findById(user_id).orElse(null);
    }

    @Override
    public void updateUser(User updateUser, int user_id) {
        User user = userRepository.findById(user_id).orElse(null);
        if (user != null){
            user.setUsername(updateUser.getUsername());
            user.setPassword(updateUser.getPassword());
            user.setFullname(updateUser.getFullname());
            user.setEmail(updateUser.getEmail());
            user.setPhone(updateUser.getPhone());
            user.setCreatedAt(updateUser.getCreatedAt());
            user.setAdmin(updateUser.getAdmin());
            userRepository.save(user);
        }
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(int user_id) {
        userRepository.deleteById(user_id);
    }

    @Override
    public List<User> searchUser(String choice, String search) {
//		trong SQL, tên cột không thể được đặt trong các tham số.
        Query query = null;
        switch (choice) {
            case "username":
                query = entityManager.createQuery("SELECT u FROM User u where u.username LIKE :search ORDER BY u.user_id ASC");
                break;
            case "fullname":
                query = entityManager.createQuery("SELECT u FROM User u where u.fullname LIKE :search ORDER BY u.user_id ASC");
                break;
            case "phone":
                query = entityManager.createQuery("SELECT u FROM User u where u.phone LIKE :search ORDER BY u.user_id ASC");
                break;
            default:
                query = entityManager.createQuery("SELECT u FROM User u where u.email LIKE :search ORDER BY u.user_id ASC");
                break;
        }
        query.setParameter("search", "%" + search + "%");
        return query.getResultList();
    }

    @Override
    public boolean isDuplicatedUsername(String username) {
        return userRepository.existsByUsername(username);
    }

}
