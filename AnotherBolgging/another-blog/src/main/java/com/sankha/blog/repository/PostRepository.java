package com.sankha.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sankha.blog.entity.Category;
import com.sankha.blog.entity.Post;
import com.sankha.blog.entity.User;

public interface PostRepository extends JpaRepository<Post, Long> {
	
		
		 List<Post> findByUser(User user);
		 List<Post> findByCategory(Category category);
		 
//		 List<Post> findByTitleContaining(String keyword);
		 @Query("select p from Post p where p.title like :key")
		 List<Post> searchByTitle(@Param("key") String keyword);
		 
		 

		
}
