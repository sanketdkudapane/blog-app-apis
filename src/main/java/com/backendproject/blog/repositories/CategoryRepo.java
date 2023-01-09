package com.backendproject.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendproject.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
