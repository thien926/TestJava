 package com.laptrinhjavaweb.response;

public class Response<T> {
	private boolean succeeded;
//    private String message;
    private String[] errors;
    private T data = null;
	
	public Response(boolean succeeded, String[] errors) {
		super();
		this.succeeded = succeeded;
		this.errors = errors;
	}
	
	public Response(boolean succeeded, String message, String[] errors, T data) {
		super();
		this.succeeded = succeeded;
//		this.message = message;
		this.errors = errors;
		this.data = data;
	}

	public Response() {}

	public boolean isSucceeded() {
		return succeeded;
	}

	public void setSucceeded(boolean succeeded) {
		this.succeeded = succeeded;
	}

//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}

	public String[] getErrors() {
		return errors;
	}

	public void setErrors(String[] errors) {
		this.errors = errors;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
