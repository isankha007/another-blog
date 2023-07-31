package com.sankha.blog.exception;

public class CategoryNotFoundException extends RuntimeException {
	String resourceName;
	String fieldName;
	Long fieldValue;
	public CategoryNotFoundException(String resourceName, String fieldName, Long fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
}
