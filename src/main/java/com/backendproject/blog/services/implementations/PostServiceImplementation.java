package com.backendproject.blog.services.implementations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.backendproject.blog.entities.Category;
import com.backendproject.blog.entities.Post;
import com.backendproject.blog.entities.User;
import com.backendproject.blog.exceptions.ResourceNotFoundException;
import com.backendproject.blog.payloads.PostDTO;
import com.backendproject.blog.payloads.PostResponse;
import com.backendproject.blog.repositories.CategoryRepo;
import com.backendproject.blog.repositories.PostRepo;
import com.backendproject.blog.repositories.UserRepo;
import com.backendproject.blog.services.PostService;

@Service
public class PostServiceImplementation implements PostService {
	@Autowired
	private PostRepo postRepo; 
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public PostDTO createPost(PostDTO postDto, Integer userId, Integer categoryId) {
		User user = userRepo.findById(userId).orElseThrow( () -> new ResourceNotFoundException("User", "user id", userId));
		Category category = categoryRepo.findById(categoryId).orElseThrow( () -> new ResourceNotFoundException("Category", "category_id", categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImgName("default.jpg");
		post.setGeneratedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		this.postRepo.save(post);
		
		Post createdPost = this.postRepo.save(post);
		return this.modelMapper.map(createdPost, PostDTO.class);
	}

	@Override
	public PostDTO updatePost(PostDTO postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImgName(postDto.getImgName());
		
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDTO.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostDTO getPostByPostId(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "post id", postId));
		return this.modelMapper.map(post, PostDTO.class);
	}

	@Override
	public List<PostDTO> getPostByUserId(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId)) ;
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDTO> postDtos = new ArrayList<>();
		for(Post post : posts) {
			postDtos.add(this.modelMapper.map(post, PostDTO.class));
		}
		//List<PostDTO> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDTO> getPostByCategoryId(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);
		List<PostDTO> postDtos = new ArrayList<>();
		for(Post post : posts) {
			postDtos.add(this.modelMapper.map(post, PostDTO.class));
		}
		/*List<PostDTO> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList()); */
		return postDtos;
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy,String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("dsc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending(); 
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> page = this.postRepo.findAll(pageable);
		List<Post> posts = page.getContent(); 
		List<PostDTO> postDtos = new ArrayList<>();
		for(Post post : posts) {
			postDtos.add(this.modelMapper.map(post, PostDTO.class));
		}
		PostResponse postRes = new PostResponse();
		postRes.setContent(postDtos);
		postRes.setPageNumber(page.getNumber());
		postRes.setTotalElements(page.getTotalElements());
		postRes.setPageSize(page.getSize());
		postRes.setLastPage(page.isLast());
		
		return postRes;
	}

	@Override
	public List<PostDTO> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDTO> postDtos = new ArrayList<PostDTO>();
		for(Post post : posts) {
			postDtos.add(this.modelMapper.map(post, PostDTO.class));
		}
		return postDtos;
	}

}
