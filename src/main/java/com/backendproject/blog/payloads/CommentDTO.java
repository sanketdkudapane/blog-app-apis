package com.backendproject.blog.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
	
	private int id;
	
	private String content;
}
