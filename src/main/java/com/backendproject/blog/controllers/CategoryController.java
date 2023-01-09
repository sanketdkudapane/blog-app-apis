package com.backendproject.blog.controllers;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import com.backendproject.blog.payloads.CategoryDTO;
import com.backendproject.blog.services.CategoryService;

import com.backendproject.blog.payloads.ApiResponse;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	//CREATE
	@PostMapping("/")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDto) {
		CategoryDTO createdCategoryDto = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDTO>(createdCategoryDto, HttpStatus.CREATED);
	}
	
	//UPDATE
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDto, @PathVariable Integer categoryId) {
		CategoryDTO updatedCategoryDto = this.categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<CategoryDTO>(updatedCategoryDto, HttpStatus.OK);
	}
	
	//DELETE
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully", true), HttpStatus.OK);
	}
		
	//GET
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer categoryId) {
		CategoryDTO categoryDto = this.categoryService.getCategory(categoryId); 
		return new ResponseEntity<CategoryDTO>(categoryDto, HttpStatus.OK);
	}
	//GET ALL
	/*@GetMapping("/")
	public ResponseEntity<List<CategoryDTO>> getAllCategories() {
		List<CategoryDTO> categories = this.categoryService.getAllCategories();
		return new ResponseEntity<List<CategoryDTO>>(categories, HttpStatus.OK); 
	}*/

}
		
