package com.sankha.blog.services;

import com.sankha.blog.payload.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto comment,Long posid,Long UserId);
	void deleteComment(Long commentid);

}
