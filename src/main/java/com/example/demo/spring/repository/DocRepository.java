package com.example.demo.spring.repository;

import com.example.demo.spring.model.entity.Doc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocRepository extends JpaRepository<Doc, Long> {
}
