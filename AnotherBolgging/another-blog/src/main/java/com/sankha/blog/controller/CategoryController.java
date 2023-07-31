package com.sankha.blog.controller;

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

import com.sankha.blog.payload.ApiResponse;
import com.sankha.blog.payload.CategoryDto;
import com.sankha.blog.payload.UserDto;
import com.sankha.blog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		 CategoryDto createCategory = categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(createCategory,HttpStatus.CREATED);
	}
	
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable("catId") Long catId) {
		 CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, catId);
		return ResponseEntity.ok(updatedCategory);
	}
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("catId") Long catId) {
		categoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully",true),HttpStatus.OK);
		//new ResponseEntity(Map.of("Message","User deleted successfully"),HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getallUser() {
		return ResponseEntity.ok(categoryService.getAllCategories());
	}
	
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getUser(@PathVariable("catId") Long catId) {
		return ResponseEntity.ok(categoryService.getCategory(catId));
	}

}
