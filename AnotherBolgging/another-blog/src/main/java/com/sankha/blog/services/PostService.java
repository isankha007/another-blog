package com.sankha.blog.services;

import java.util.List;

import com.sankha.blog.payload.PostDto;
import com.sankha.blog.payload.PostResponse;

public interface PostService {
	PostDto createPost(PostDto postDto,Long userId,Long categoryId);
	
	PostDto updatePost(PostDto postDto,Long postId);

	void deletePost(Long postId);
	
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sorDir);
	
	PostDto getPostById(Long postId);
	
	PostResponse getPostByCategoryId(Long categoryId,Integer pageNumber,Integer pageSize);
	
	PostResponse getAllPostByUser(Long userID,Integer pageNumber,Integer pageSize);
	
	List<PostDto> getPostBykeyword(String keyword);



}
