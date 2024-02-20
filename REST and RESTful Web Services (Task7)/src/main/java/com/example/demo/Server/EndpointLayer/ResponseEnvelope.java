package com.example.demo.Server.EndpointLayer;

import java.util.List;

public class ResponseEnvelope {

	private Object responseData;
	private EHTTPStatus httpStatus;

	/**
	 * @param responseData
	 * @param httpStatus
	 */
	public ResponseEnvelope(Object responseData, EHTTPStatus httpStatus) {
		this.responseData = responseData;
		this.httpStatus = httpStatus;
	}

	/**
	 * @return the responseData
	 */
	public Object getResponseData() {
		return responseData;
	}

	/**
	 * @param responseData the responseData to set
	 */
	public void setResponseData(Object responseData) {
		this.responseData = responseData;
	}

	/**
	 * @return the httpStatus
	 */
	public EHTTPStatus getHttpStatus() {
		return httpStatus;
	}

	/**
	 * @param httpStatus the httpStatus to set
	 */
	public void setHttpStatus(EHTTPStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	@Override
	public String toString() {
		if (responseData instanceof List) {
			List<?> meetings = (List<?>) responseData;
			StringBuilder sb = new StringBuilder();
			sb.append("HttpStatus Response (").append(httpStatus.getStatusCode()).append(" (" + httpStatus + ")");
			for (Object m : meetings) {
				sb.append("\n").append("Meeting: ").append(m.toString());
			}
			return sb.toString();
		} else
			return "HttpStatus Response " + httpStatus.getStatusCode() + " (" + httpStatus + ")" + "\nMeeting: "
					+ responseData;
	}

}
