package com.sankha.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sankha.blog.payload.ApiResponse;
import com.sankha.blog.payload.UserDto;
import com.sankha.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createUserDto = userService.createUser(userDto);
		return new ResponseEntity<UserDto>(createUserDto,HttpStatus.CREATED);
	}
	
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,@PathVariable("userId") Long userId) {
		UserDto updatedUserDto = userService.updateUser(userDto,userId);
		return ResponseEntity.ok(updatedUserDto);
	}
	
	@PreAuthorize("hasRole(ADMIN)")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Long userId) {
		userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);
		//new ResponseEntity(Map.of("Message","User deleted successfully"),HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getallUser() {
		return ResponseEntity.ok(userService.getallUser());
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable("userId") Long userId) {
		return ResponseEntity.ok(userService.getUserById(userId));
	}
}
