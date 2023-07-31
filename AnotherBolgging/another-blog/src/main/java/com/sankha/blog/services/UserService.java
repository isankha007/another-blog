package com.sankha.blog.services;

import com.sankha.blog.payload.UserDto;
import java.util.List;

public interface UserService {
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Long userId);
	UserDto getUserById(Long userId);
	List<UserDto> getallUser();
	void deleteUser(Long userId);
	UserDto registerNewUser(UserDto user);


}
