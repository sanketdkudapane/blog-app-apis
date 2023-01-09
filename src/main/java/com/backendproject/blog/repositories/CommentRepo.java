package com.backendproject.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendproject.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
