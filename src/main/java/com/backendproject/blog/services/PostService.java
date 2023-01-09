package com.backendproject.blog.services;

import java.util.List;

import com.backendproject.blog.entities.Post;
import com.backendproject.blog.payloads.PostDTO;
import com.backendproject.blog.payloads.PostResponse;

public interface PostService {
	//CREATE
	PostDTO createPost(PostDTO postDto, Integer userId, Integer categoryId);
	
	//UPDATE
	PostDTO updatePost(PostDTO postDto, Integer postId);
	
	//DELETE
	void deletePost(Integer postId);
	
	//GET POST BY Post Id
	PostDTO getPostByPostId(Integer postId);
	
	//GET All POSTS BY User Id
	List<PostDTO> getPostByUserId(Integer userId);
	
	//GET All POSTS BY Category Id
	List<PostDTO> getPostByCategoryId(Integer categoryId);
	
	//Get All Post
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//Search Post by keyword
	List<PostDTO> searchPosts(String keyword);
}
