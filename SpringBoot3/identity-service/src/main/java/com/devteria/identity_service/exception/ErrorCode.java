package com.devteria.identity_service.exception;

public enum ErrorCode {
	UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error"),
    USER_EXISTED(400, "User existed"),
	UNAUTHENTICATED(401, "UnAuthenticated")
    ;
	
	private ErrorCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	private int code;
	private String message;
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
