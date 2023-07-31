package com.sankha.blog.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sankha.blog.entity.Category;
import com.sankha.blog.entity.Post;
import com.sankha.blog.entity.User;
import com.sankha.blog.exception.CategoryNotFoundException;
import com.sankha.blog.exception.ResourceNotFoundException;
import com.sankha.blog.payload.PostDto;
import com.sankha.blog.payload.PostResponse;
import com.sankha.blog.repository.CategoryRepository;
import com.sankha.blog.repository.PostRepository;
import com.sankha.blog.repository.UserRepository;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
	private PostRepository postRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
	
	@Override
	public PostDto createPost(PostDto postDto,Long userId,Long categoryId) {
		// TODO Auto-generated method stub
		User user=userRepository.findById(userId).orElseThrow
		(()->new ResourceNotFoundException("User", "User Id", userId));
		
		Category category = categoryRepository.findById(categoryId).orElseThrow
			(()-> new CategoryNotFoundException("Category ", "Category Id", categoryId));
		
		Post post = modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setCreatedAt(new Date());
		post.setCategory(category);
		post.setUser(user);
		Post savedPsost = postRepository.save(post);
		return modelMapper.map(savedPsost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Long postId) {
		// TODO Auto-generated method stub
		  Post post = postRepository.findById(postId).orElseThrow
				(()-> new ResourceNotFoundException("post ", "post Id", postId));
			post.setImageName(postDto.getImageName());
			//post.setCreatedAt(new Date());
			//post.setCategory(postDto.getCategory());
			post.setTitle(postDto.getTitle());
			post.setContent(postDto.getContent());
			Post savedPsost = postRepository.save(post);
			return modelMapper.map(savedPsost, PostDto.class);
		//return null;
	}

	@Override
	public void deletePost(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow
				(()-> new ResourceNotFoundException("post ", "post Id", postId));
		postRepository.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		// TODO Auto-generated method stub
		Sort sort=null; 
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}else {
			sort=Sort.by(sortBy).descending();
		}
		
		Pageable  pg= PageRequest.of(pageNumber, pageSize,sort);
		
		Page<Post> pagePost=postRepository.findAll(pg);
		List<Post> findAllPosts=pagePost.getContent();
		List<PostDto> postDtos = findAllPosts.stream().map((p)-> modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setTotlaElements(pagePost.getTotalElements());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Long postId) {
		// TODO Auto-generated method stub
		Post findPostById = postRepository.findById(postId)
				.orElseThrow(()->new ResourceNotFoundException("Post", "Post Id", postId));
		return modelMapper.map(findPostById, PostDto.class);
	}

	@Override
	public PostResponse getPostByCategoryId(Long categoryId,Integer pageNumber,Integer pageSize) {
		// TODO Auto-generated method stub
		
		Category category = categoryRepository.findById(categoryId).orElseThrow
				(()-> new CategoryNotFoundException("Category ", "Category Id", categoryId));
		
		List<Post> postByCategory = postRepository.findByCategory(category);
		
		List<PostDto> postDtos = postByCategory.stream().map((p)-> modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
		
		Pageable  pg= PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePost=postRepository.findAll(pg);
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setTotlaElements(pagePost.getTotalElements());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostResponse getAllPostByUser(Long userId,Integer pageNumber,Integer pageSize) {

		User user=userRepository.findById(userId).orElseThrow
		(()->new ResourceNotFoundException("User", "User Id", userId));
		
		 List<Post> postByUser = postRepository.findByUser(user);
		
		List<PostDto> posts = postByUser.stream().map(p-> modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
	
		Pageable  pg= PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePost=postRepository.findAll(pg);
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(posts);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setTotlaElements(pagePost.getTotalElements());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;		
	}

	@Override
	public List<PostDto> getPostBykeyword(String keyword) {
		// TODO Auto-generated method stub
		List<Post> findByTitleContaining = postRepository.searchByTitle("%"+keyword+"%");
		List<PostDto> posts = findByTitleContaining.stream().map(p-> modelMapper.map(p, PostDto.class)).collect(Collectors.toList());

		return posts;
	}

	

}
