package com.example.demo.Server.EndpointLayer;

public enum EHTTPStatus {
	OK(200, "ok"), CREATED(201, "created"), NOTMODIFIED(304, "not modified"), BADREQUEST(400, "bad request"),
	NOTFOUND(404, "not found");

	private int statusCode;
	private String statusMessage;

	EHTTPStatus(int statusCode, String statusMessage) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @return the statusMessage
	 */
	public String getStatusMessage() {
		return statusMessage;
	}

}
