package com.sankha.blog.services;

import java.util.List;

import com.sankha.blog.payload.CategoryDto;

public interface CategoryService {

	 CategoryDto createCategory(CategoryDto categoryDto);
	 CategoryDto updateCategory(CategoryDto categoryDto,Long categoryId);
	 void deleteCategory(Long categoryId);
	 CategoryDto getCategory(Long categoryId);
	 List<CategoryDto> getAllCategories();
}
