package com.backendproject.blog.controllers;

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

import com.backendproject.blog.exceptions.ApiException;
import com.backendproject.blog.payloads.ApiResponse;
import com.backendproject.blog.payloads.JwtAuthRequest;
import com.backendproject.blog.payloads.JwtAuthResponse;
import com.backendproject.blog.payloads.UserDTO;
import com.backendproject.blog.security.JwtTokenHelper;
import com.backendproject.blog.services.UserService;

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
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken( @RequestBody JwtAuthRequest request) throws Exception {
		
		this.authenticate(request.getUsername(), request.getPassword());
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		System.out.println("PASSWORD: " + userDetails.getPassword());
		String token = this.jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
			this.authenticationManager.authenticate(authenticationToken);
		}
		catch(BadCredentialsException ex) {
			System.out.println("Invalid Details !!");
			throw new ApiException("Invalid username OR password !!");
		}
		catch(Exception ex) {
			throw new Exception("Exception: "+ ex.getMessage());
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser (@RequestBody UserDTO userDto) {
		UserDTO registeredUser = this.userService.registerUser(userDto);
		
		return new ResponseEntity<UserDTO>(registeredUser, HttpStatus.CREATED);
	}
}
