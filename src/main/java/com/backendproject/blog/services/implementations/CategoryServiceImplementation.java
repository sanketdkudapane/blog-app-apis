package com.backendproject.blog.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backendproject.blog.entities.Category;
import com.backendproject.blog.exceptions.ResourceNotFoundException;
import com.backendproject.blog.payloads.CategoryDTO;
import com.backendproject.blog.repositories.CategoryRepo;
import com.backendproject.blog.services.CategoryService;

@Service
public class CategoryServiceImplementation implements CategoryService {
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category createdCategory = this.categoryRepo.save(category);
		return this.modelMapper.map(createdCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDto, Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedCategory = this.categoryRepo.save(category);
		return this.modelMapper.map(updatedCategory, CategoryDTO.class);
	}

	@Override
	public CategoryDTO getCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		return this.modelMapper.map(category, CategoryDTO.class);
	}

	/*@Override
	public List<CategoryDTO> getAllCategories() {
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDTO> categoriesDTO = new ArrayList<CategoryDTO>();
		for (Category category : categories) {
			categoriesDTO.add(this.modelMapper.map(category, CategoryDTO.class));
		}
		
		//List<CategoryDTO> categoriesDTO = categories.stream().map((catg) -> this.modelMapper.map(catg,  CategoryDTO.class)).collect(Collectors.toList());
		return categoriesDTO;
	}*/

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		this.categoryRepo.delete(category);
	}

}
