package com.mkhan.common;

import org.springframework.stereotype.Component;

@Component("customMessage")
public class CustomMessage {

	private String message ;
	private String resultCode;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
}
