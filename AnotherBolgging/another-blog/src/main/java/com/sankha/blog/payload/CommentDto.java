package com.sankha.blog.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

	private Long Id;
	
	private String content;
	
	private UserDto user;
}
