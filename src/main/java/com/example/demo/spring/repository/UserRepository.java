package com.example.demo.spring.repository;

import com.example.demo.spring.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
