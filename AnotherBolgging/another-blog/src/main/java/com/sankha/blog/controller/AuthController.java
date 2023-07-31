package com.sankha.blog.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sankha.blog.entity.User;
import com.sankha.blog.exception.ApiException;
import com.sankha.blog.payload.JwtAuthRequest;
import com.sankha.blog.payload.JwtAuthResponse;
import com.sankha.blog.payload.UserDto;
import com.sankha.blog.security.JwtTokenHelper;
import com.sankha.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserService userService;
	
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> creatToken(@RequestBody JwtAuthRequest request) throws Exception {
		authenticate(request.getUsername(), request.getPassword());
		
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
		//try {
		//authenticationManager.authenticate(authenticationToken);
		//}
//		catch (DisabledException e) {
//			throw Exception(us)
//		}
		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		String token = jwtTokenHelper.generateToken(userDetails);
		
		JwtAuthResponse response=new JwtAuthResponse();
		response.setToken(token);
		response.setUser(this.mapper.map((User) userDetails, UserDto.class));

		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
	}
	
	
	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		try {

			this.authenticationManager.authenticate(authenticationToken);

		} catch (BadCredentialsException e) {
			System.out.println("Invalid Detials !!");
			throw new ApiException("Invalid username or password !!");
		}

	}
	
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto) {
		UserDto registeredUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
	}

}
