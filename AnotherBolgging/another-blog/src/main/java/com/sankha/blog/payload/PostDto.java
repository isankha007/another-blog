package com.sankha.blog.payload;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	private long postId;
	
	private String title;
	
	private String content;
	
	private String imageName;
	
	private Date  createdAt;

    private CategoryDto category;

	private UserDto user;
	
	private List<CommentDto> comments;
	//private Set<CommentDto> comments; //=new HashSet<>();
	

}
