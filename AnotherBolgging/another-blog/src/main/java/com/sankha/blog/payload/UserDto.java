package com.sankha.blog.payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.sankha.blog.entity.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDto {
	private Long id;
	@NotEmpty
	@Size(min = 4,message = "user name must be min of 4 characters")
	private String name;
	@Email(message = "email is not valid")
	private String email;
	@NotEmpty
	@Size(min = 3,max = 10,message = "min 3 max 10")
	//@Pattern
	private String password;
	@NotEmpty
	private String about;
	
	private Set<RoleDto> roles=new HashSet<>();
}
