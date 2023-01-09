package com.backendproject.blog.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendproject.blog.entities.Comment;
import com.backendproject.blog.entities.Post;
import com.backendproject.blog.exceptions.ResourceNotFoundException;
import com.backendproject.blog.payloads.CommentDTO;
import com.backendproject.blog.repositories.CommentRepo;
import com.backendproject.blog.repositories.PostRepo;
import com.backendproject.blog.services.CommentService;

@Service
public class CommentServiceImplemantation implements CommentService{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		Comment comment = this.modelMapper.map(commentDTO, Comment.class);
		
		comment.setPost(post);
		Comment createdComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(createdComment, CommentDTO.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Comment Id", commentId));
		this.commentRepo.delete(comment);
	}
	
	
}
