package com.yuale01.mis.exception;

public class ErrorMessage 
{
	private int statusCode;
	private int errorCode;
	private String message;
	
	public ErrorMessage(int statusCode, int errorCode, String message)
	{
		this.statusCode = statusCode;
		this.errorCode = errorCode;
		this.message = message;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

}
