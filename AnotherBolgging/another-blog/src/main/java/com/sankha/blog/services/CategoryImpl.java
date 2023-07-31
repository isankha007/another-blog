package com.sankha.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sankha.blog.entity.Category;
import com.sankha.blog.exception.CategoryNotFoundException;
import com.sankha.blog.payload.CategoryDto;
import com.sankha.blog.repository.CategoryRepository;

@Service
public class CategoryImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		Category mapCategory = mapper.map(categoryDto, Category.class);
		Category savedCat = categoryRepository.save(mapCategory);
		return mapper.map(savedCat,CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> 
		new CategoryNotFoundException("Category","CategoryId",categoryId));
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		category.setCategoryName(categoryDto.getCategoryName());
		Category updatedCat = categoryRepository.save(category);

		return mapper.map(updatedCat,CategoryDto.class);
	}

	@Override
	public void deleteCategory(Long categoryId) {
		categoryRepository.findById(categoryId).orElseThrow(()-> 
		new CategoryNotFoundException("Category","CategoryId",categoryId));
		categoryRepository.deleteById(categoryId);
	}

	@Override
	public CategoryDto getCategory(Long categoryId) {
		Category category = categoryRepository.findById(categoryId).orElseThrow(()-> 
		new CategoryNotFoundException("Category","CategoryId",categoryId));
		return mapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		// TODO Auto-generated method stub
		List<Category> allCategories = categoryRepository.findAll();
		List<CategoryDto> categories = allCategories.stream().map(cat->mapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return categories;
	}

	

}
