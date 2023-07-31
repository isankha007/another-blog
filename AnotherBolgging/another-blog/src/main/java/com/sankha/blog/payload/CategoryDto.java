package com.sankha.blog.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	private Long categoryId;
	@NotBlank
	@Size(min=4,message = "min is 4")
	private String categoryName;
	@NotBlank
	@Size(min=10,message = "min is 10")
	private String categoryDescription;
}
