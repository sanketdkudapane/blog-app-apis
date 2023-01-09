package com.backendproject.blog.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
	private int id;
	
	@NotNull
	@NotEmpty(message = "Name must not be empty")
	@Size(min = 3, message = "Name should contain at least of 3 characters")
	private String name;
	
	@Email(message = "Email is invalid")
	@NotEmpty
	private String email;
	
	@NotNull
	@Size(min = 3, max = 10, message = "Password must be between 3 to 10 characters")
	@NotEmpty(message = "Password must not be empty")
	private String password;
	
	@NotNull
	@NotEmpty(message = "About must not be empty")
	private String about;
}
