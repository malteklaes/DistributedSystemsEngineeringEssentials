package com.example.demo.Client;

public enum EHTTPRequest {

	GET("get msg"), POST("post msg"), DELETE("delete msg");

	private String requestMessage;

	EHTTPRequest(String requestMessage) {
		this.requestMessage = requestMessage;
	}

	public String getRequestMessage() {
		return requestMessage;
	}

}
