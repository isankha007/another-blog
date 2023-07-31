package com.sankha.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sankha.blog.payload.ApiResponse;
import com.sankha.blog.payload.CommentDto;
import com.sankha.blog.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	@Autowired
	private CommentService commentService;

	@PostMapping("/post/{postId}/user/{userId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
			@PathVariable Long postId,
			@PathVariable Long userId){
		CommentDto comment = commentService.createComment(commentDto, postId,userId);
		return new ResponseEntity<CommentDto>(comment,HttpStatus.CREATED);
	}
	
	@DeleteMapping(name = "/comments/{commentId}")
	public ApiResponse createComment(@PathVariable Long commentId){
		commentService.deleteComment(commentId);
		return new ApiResponse("Comment deleted",true);
	}
}
