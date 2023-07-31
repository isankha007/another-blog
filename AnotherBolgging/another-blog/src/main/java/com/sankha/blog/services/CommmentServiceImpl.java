package com.sankha.blog.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sankha.blog.entity.Comment;
import com.sankha.blog.entity.Post;
import com.sankha.blog.entity.User;
import com.sankha.blog.exception.ResourceNotFoundException;
import com.sankha.blog.payload.CommentDto;
import com.sankha.blog.repository.CommentRepository;
import com.sankha.blog.repository.PostRepository;
import com.sankha.blog.repository.UserRepository;

@Service
public class CommmentServiceImpl implements CommentService {
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public CommentDto createComment(CommentDto commentDto, Long postId,Long userId) {
		// TODO Auto-generated method stub
		Post post = postRepository.findById(postId).orElseThrow
				(()-> new ResourceNotFoundException("post ", "post Id", postId));
		
		User user=userRepository.findById(userId).orElseThrow
				(()->new ResourceNotFoundException("User", "User Id", userId));
		
		//Set<Comment> comments = post.getComments();
		//comments.add);
		Comment comment = modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		comment.setUser(user);
		Comment savedComment= commentRepository.save(comment);
		
		return modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Long commentid) {
		// TODO Auto-generated method stub
		 Comment comment = commentRepository.findById(commentid).orElseThrow
			(()-> new ResourceNotFoundException("Comment ", "Comment Id", commentid));
		 commentRepository.delete(comment);
	}

}
