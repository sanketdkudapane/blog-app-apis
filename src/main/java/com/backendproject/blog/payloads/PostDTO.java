package com.backendproject.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.backendproject.blog.entities.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {
	
	private String title;
	
	private String content;
	
	private String imgName;
	
	private Date generatedDate;
	
	private CategoryDTO category;
	
	private UserDTO user;
	
	private Set<CommentDTO> comments = new HashSet<>();
	
}
