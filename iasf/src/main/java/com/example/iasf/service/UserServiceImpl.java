package com.example.iasf.service;

import com.example.iasf.entity.User;
import com.example.iasf.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<User> findAll() {
        return repo.findAll();
    }

    @Override
    public List<User> search(String keyword) {
        if (keyword == null || keyword.isBlank()) return repo.findAll();
        return repo.findByNameContainingIgnoreCase(keyword.trim());
    }

    @Override
    public User findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public User save(User user) {   // id == null -> insert, có id -> update
        return repo.save(user);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public boolean isNameExist(String name) {
        return repo.existsByNameIgnoreCase(name.trim());
    }

    @Override
    public boolean isNameExistForOther(String name, Long id) {
        return repo.existsByNameIgnoreCaseAndIdNot(name.trim(), id);
    }
}