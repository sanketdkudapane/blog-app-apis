package com.backendproject.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendproject.blog.entities.Category;
import com.backendproject.blog.entities.Post;
import com.backendproject.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String keyword);
}
