package com.devteria.identity_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
	UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(400, "User existed", HttpStatus.BAD_REQUEST),
	UNAUTHENTICATED(401, "UnAuthenticated", HttpStatus.UNAUTHORIZED),
	UNAUTHORIZEED(403, "Access denied", HttpStatus.FORBIDDEN)
    ;
	
	private int code;
	private String message;
	private HttpStatusCode statusCode;
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
