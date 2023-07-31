package com.sankha.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sankha.blog.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	

}
