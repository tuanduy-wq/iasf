package com.example.iasf.service;

import com.example.iasf.entity.User;
import java.util.List;

public interface UserService {
    List<User> findAll();
    List<User> search(String keyword);
    User findById(Long id);
    User save(User user);
    void delete(Long id);
    boolean isNameExist(String name);
    boolean isNameExistForOther(String name, Long id);
}