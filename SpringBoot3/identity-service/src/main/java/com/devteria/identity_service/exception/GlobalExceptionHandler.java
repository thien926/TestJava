package com.devteria.identity_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devteria.identity_service.dto.request.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(value = Exception.class)
	ResponseEntity<ApiResponse<Object>> handlingRuntimeException(Exception exception) {
		return ResponseEntity.badRequest().body(ApiResponse.builder()
		        .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
		        .message(exception.getMessage())
//		        .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
		        .result(null)
		        .build());
	}
	
	@ExceptionHandler(value = AppException.class)
	ResponseEntity<ApiResponse<Object>> handlingAppException(AppException exception) {
		ErrorCode errorCode = exception.getErrorCode();
		return ResponseEntity.badRequest().body(ApiResponse.builder()
		        .code(errorCode.getCode())
		        .message(errorCode.getMessage())
		        .result(null)
		        .build());
	}
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	ResponseEntity<ApiResponse<Object>> handlingValidation(MethodArgumentNotValidException exception) {
		return ResponseEntity.badRequest().body(ApiResponse.builder()
		        .code(HttpStatus.BAD_REQUEST.value())
		        .message(exception.getFieldError().getDefaultMessage())
		        .result(null)
		        .build());
	}
}
