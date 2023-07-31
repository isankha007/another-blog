package com.sankha.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sankha.blog.config.AppConstants;
import com.sankha.blog.payload.ApiResponse;
import com.sankha.blog.payload.PostDto;
import com.sankha.blog.payload.PostResponse;
import com.sankha.blog.services.FileService;
import com.sankha.blog.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Long userId,
			@PathVariable Long categoryId) {
		PostDto createPost = postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostByUser(@PathVariable Long userId,
			@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue =AppConstants.PAGE_SIZE,required = false)Integer pageSize){
		PostResponse allPostByUser = postService.getAllPostByUser(userId,pageNumber,pageSize);
		return new ResponseEntity<PostResponse>(allPostByUser,HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getPostByCategory(@PathVariable Long categoryId,
			@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue =AppConstants.PAGE_SIZE,required = false)Integer pageSize){
		 PostResponse postByCategoryId = postService.getPostByCategoryId(categoryId,pageNumber,pageSize);
		return new ResponseEntity<PostResponse>(postByCategoryId,HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue =AppConstants.PAGE_SIZE,required = false)Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue =AppConstants.SORT_BY,required = false)String sortBy,
			@RequestParam(value = "sortDir",defaultValue =AppConstants.SORT_DIR,required = false)String sortDir){
		PostResponse allPost = postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(allPost,HttpStatus.OK);
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getAllPost(@PathVariable Long postId){
		 PostDto postById = postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postById,HttpStatus.OK);
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Long postId ) {
		PostDto createPost = postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Long postId){
		postService.deletePost(postId);
		return new ApiResponse("Post Deleted",true);
	}
	
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> getPostByTitle(@PathVariable String keyword) {
		List<PostDto> postBykeyword = postService.getPostBykeyword(keyword);
		return new ResponseEntity<List<PostDto>>(postBykeyword,HttpStatus.CREATED);
	}
	
	//post image Upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(
			@RequestParam("image") MultipartFile image,@PathVariable Long postId
			) {
		PostDto postById = postService.getPostById(postId);

		String filename="default.png";
		try {
			filename = fileService.uploadImage(path, image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		postById.setImageName(filename);
		PostDto updatedPost = postService.updatePost(postById, postId);
		return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);

	}
	
	//method to serve files
    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream())   ;

    }
	
}
