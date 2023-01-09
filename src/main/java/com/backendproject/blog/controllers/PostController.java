package com.backendproject.blog.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backendproject.blog.config.AppConstants;
import com.backendproject.blog.entities.Post;
import com.backendproject.blog.payloads.ApiResponse;
import com.backendproject.blog.payloads.PostDTO;
import com.backendproject.blog.payloads.PostResponse;
import com.backendproject.blog.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	PostService postService;
	@Autowired
	ModelMapper modelMapper;
	
	//CREATE Post
	@PostMapping("/post/user/{user_id}/category/{category_id}")
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDto, @PathVariable Integer user_id, @PathVariable Integer category_id) 
	{
		PostDTO createdPost = this.postService.createPost(postDto, user_id, category_id);
		return new ResponseEntity<PostDTO>(createdPost, HttpStatus.CREATED);
	}
	
	//GET POST by userId
	@GetMapping("/users/{userId}/posts")
	public ResponseEntity<List<PostDTO>> getPostByUserId(@PathVariable Integer userId)
	{
		List<PostDTO> fetchedPosts = this.postService.getPostByUserId(userId);
		return new ResponseEntity<List<PostDTO>>(fetchedPosts, HttpStatus.OK);
	}
	
	//GET POST by categoryId
	@GetMapping("/categories/{categotyId}/posts")
	public ResponseEntity<List<PostDTO>> getPostByCategoryId(@PathVariable Integer categotyId)
	{
		List<PostDTO> fetchedPosts = this.postService.getPostByCategoryId(categotyId);
		return new ResponseEntity<List<PostDTO>>(fetchedPosts, HttpStatus.OK);
	}
	
	//GET POST by PostID
		@GetMapping("/posts/{postId}")
		public ResponseEntity<PostDTO> getPostByPostId(@PathVariable Integer postId)
		{
			PostDTO post = this.postService.getPostByPostId(postId);
			return new ResponseEntity<PostDTO>(post, HttpStatus.OK);
		}
	
	//GET ALL POSTS
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber, 
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir)
	{
		PostResponse posts =  this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(posts, HttpStatus.OK);
	}
	
	//UPDATE Post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDto, @PathVariable Integer postId)
	{
		PostDTO updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.OK);
	}
	
	//DELETE Post
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) 
	{
		this.postService.deletePost(postId);
		return new ApiResponse("Post successfully deleted!!", true);
	}
	
	//Search Post based on keyword
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDTO>> searchPost(@PathVariable String keyword)
	{
		List<PostDTO> searchedPosts = this.postService.searchPosts(keyword);
		return new ResponseEntity<List<PostDTO>>(searchedPosts, HttpStatus.OK);
	} 
}
