package com.devteria.identity_service.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private ErrorCode errorCode;
}
