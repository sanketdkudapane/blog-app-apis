package com.backendproject.blog.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backendproject.blog.config.AppConstants;
import com.backendproject.blog.entities.Role;
import com.backendproject.blog.entities.User;
import com.backendproject.blog.payloads.UserDTO;
import com.backendproject.blog.repositories.RoleRepo;
import com.backendproject.blog.repositories.UserRepo;
import com.backendproject.blog.services.UserService;
import com.backendproject.blog.exceptions.*;

@Service
public class UseServiceImplementation implements UserService {
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDTO createUser(UserDTO userDto) {
		User user = this.dtoToUserEntity(userDto);

		User savedUser = this.userRepo.save(user);
		return this.userEntityToDto(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDto, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		user.setAbout(userDto.getAbout());

		User updatedUser = this.userRepo.save(user);
		return this.userEntityToDto(updatedUser);
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		return this.userEntityToDto(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		List<UserDTO> userDtos = users.stream().map(user -> this.userEntityToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		this.userRepo.delete(user);

	}

	private User dtoToUserEntity(UserDTO userDTO) {
//		User user = new User();
//		user.setId(userDTO.getId());
//		user.setEmail(userDTO.getEmail());
//		user.setName(userDTO.getName());
//		user.setAbout(userDTO.getAbout());
//		user.setPassword(userDTO.getPassword());

		User user = this.modelMapper.map(userDTO, User.class);
		return user;
	}

	private UserDTO userEntityToDto(User user) {
//		UserDTO userDTO = new UserDTO();
//		userDTO.setId(user.getId());
//		userDTO.setEmail(user.getEmail());
//		userDTO.setName(user.getName());
//		userDTO.setAbout(user.getAbout());
//		userDTO.setPassword(user.getPassword());
		UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
		return userDTO;
	}

	@Override
	public UserDTO registerUser(UserDTO userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		
		//Before saving user encode its password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//set role of user
		Role role = this.roleRepo.findById(AppConstants.ROLE_USER).get();
		user.getRoles().add(role);  
		
		User registeredUser = this.userRepo.save(user);
		return this.modelMapper.map(registeredUser, UserDTO.class);
	}
}
