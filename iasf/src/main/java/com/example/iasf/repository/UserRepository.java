package com.example.iasf.repository;

import com.example.iasf.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // Search: chứa từ khóa, không phân biệt hoa thường
    List<User> findByNameContainingIgnoreCase(String name);

    // Kiểm tra trùng tên khi tạo mới
    boolean existsByNameIgnoreCase(String name);

    // Kiểm tra trùng tên khi update (bỏ qua chính nó)
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
}