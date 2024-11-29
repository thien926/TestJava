package com.devteria.identity_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = RuntimeException.class)	// Đánh dấu phương thức để xử lý loại ngoại lệ cụ thể, ở đây là RuntimeException
	ResponseEntity<String> handlingRuntimeException(RuntimeException exception) {
		return ResponseEntity.badRequest().body(exception.getMessage());
	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	ResponseEntity<String> handlingValidation(MethodArgumentNotValidException exception) {
		return ResponseEntity.badRequest().body(exception.getFieldError().getDefaultMessage());
	}
}
