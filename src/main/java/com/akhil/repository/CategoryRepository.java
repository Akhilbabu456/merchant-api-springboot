package com.akhil.repository;

import java.util.Optional;

import com.akhil.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  Optional<Category> findByCategory(String category);
}
