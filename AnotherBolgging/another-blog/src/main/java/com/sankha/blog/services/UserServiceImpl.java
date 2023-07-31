package com.sankha.blog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sankha.blog.config.AppConstants;
import com.sankha.blog.entity.Role;
import com.sankha.blog.entity.User;
import com.sankha.blog.exception.ResourceNotFoundException;
import com.sankha.blog.payload.UserDto;
import com.sankha.blog.repository.RoleRepository;
import com.sankha.blog.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository	roleRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		User user=dtoToUser(userDto);
		User tempUser=userRepo.save(user);
		return userToDto(tempUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Long userId) {
		// TODO Auto-generated method stub
		User user =userRepo.findById(userId).orElseThrow(()->
		new ResourceNotFoundException("user","id",userId));
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		User updatedUser=userRepo.save(user);
		return userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Long userId) {
		// TODO Auto-generated method stub
		User user =userRepo.findById(userId).orElseThrow(()->		
		new ResourceNotFoundException("user","id",userId));


		return userToDto(user);
	}

	@Override
	public List<UserDto> getallUser() {
		// TODO Auto-generated method stub
		List<User> users = userRepo.findAll();

		List<UserDto> usersDto = users.stream().map(u->userToDto(u)).collect(Collectors.toList());
		return usersDto;
	}

	@Override
	public void deleteUser(Long userId) {
		// TODO Auto-generated method stub
		userRepo.findById(userId).orElseThrow(()->		
		new ResourceNotFoundException("user","id",userId));
		userRepo.deleteById(userId);
		
	}
	
	private User dtoToUser(UserDto userdto) {
		User user=modelMapper.map(userdto, User.class);  //new User();
	
		/*
		 * user.setId(userdto.getId()); user.setAbout(userdto.getAbout());
		 * user.setEmail(userdto.getEmail()); user.setName(userdto.getName());
		 * user.setPassword(userdto.getPassword());
		 */
		return user;

		
	}
	
	public UserDto userToDto(User user) {
		UserDto userdto=modelMapper.map(user, UserDto.class);
				/*new UserDto();
		userdto.setId(user.getId());
		userdto.setAbout(user.getAbout());
		userdto.setEmail(user.getEmail());
		userdto.setName(user.getName());
		userdto.setPassword(user.getPassword());*/
		return userdto;

		
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {

		User user = modelMapper.map(userDto, User.class);
		
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		// roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

		user.getRoles().add(role);

		User newUser = this.userRepo.save(user);

		return this.modelMapper.map(newUser, UserDto.class);
		//return null;
	}

}
