package com.example.LTJV_CuoiKy.repository;

import com.example.LTJV_CuoiKy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
