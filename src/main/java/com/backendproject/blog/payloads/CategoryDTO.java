package com.backendproject.blog.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {
	
	private Integer categoryId;
	
	@NotNull
	@NotEmpty
	private String categoryTitle;
	
	@NotNull
	@NotEmpty
	private String categoryDescription;
}
