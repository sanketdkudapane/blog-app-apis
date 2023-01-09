package com.backendproject.blog.services;

import com.backendproject.blog.payloads.CommentDTO;

public interface CommentService {
	
	CommentDTO createComment(CommentDTO commentDTO, Integer postId);
	
	void deleteComment(Integer commentId);
}
