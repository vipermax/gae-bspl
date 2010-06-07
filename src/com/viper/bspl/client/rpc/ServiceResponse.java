package com.viper.bspl.client.rpc;

import java.io.Serializable;

public class ServiceResponse implements Serializable {
	
	private boolean success;
	private String message;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
