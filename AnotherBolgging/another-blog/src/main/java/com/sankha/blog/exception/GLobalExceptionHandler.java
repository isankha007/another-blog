package com.sankha.blog.exception;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sankha.blog.payload.ApiResponse;

@RestControllerAdvice
public class GLobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundHandler(ResourceNotFoundException ex) {
		String message=ex.getMessage();
		ApiResponse apiResponse=new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND); 
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleMethodargumentValidException(MethodArgumentNotValidException ex) {
		Map<String,String> errorRes=new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((err)->{
			String fieldName = ((FieldError)err).getField();
			String defaultMessage = err.getDefaultMessage();
			errorRes.put(fieldName, defaultMessage);
		});
		
		return new ResponseEntity<>(errorRes,HttpStatus.BAD_REQUEST); 
	}
	
	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<ApiResponse> categoryNotFoundHandler(CategoryNotFoundException ex) {
		String message=ex.getMessage();
		ApiResponse apiResponse=new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND); 
	}
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handleApiException(ApiException ex) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, true);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}
}
