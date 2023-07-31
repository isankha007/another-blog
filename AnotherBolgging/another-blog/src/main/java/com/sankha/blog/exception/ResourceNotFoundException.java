package com.sankha.blog.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String resourceName;
	String fieldName;
	Long fieldValue;
	String fieldValueString;
	public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	public ResourceNotFoundException(String resourceName, String fieldName, String fieldValueString) {
		super(String.format("%s not found with %s : %s", resourceName,fieldName,fieldValueString));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValueString = fieldValueString;
	}
	
	
}
