package com.backendproject.blog.services;

import java.util.List;

import com.backendproject.blog.payloads.CategoryDTO;

public interface CategoryService {
	CategoryDTO createCategory(CategoryDTO categoryDto);
	CategoryDTO updateCategory(CategoryDTO categoryDto, Integer categoryId);
	CategoryDTO getCategory(Integer categoryId);
	//List<CategoryDTO> getAllCategories();
	void deleteCategory(Integer categoryId);
}
