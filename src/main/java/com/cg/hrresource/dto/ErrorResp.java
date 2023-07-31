package com.cg.hrresource.dto;

import java.time.LocalDateTime;

public class ErrorResp {
	private LocalDateTime timeStamp;;
	private String message;

	public ErrorResp() {
		super();
	}

	public ErrorResp(LocalDateTime timeStamp, String message) {
		super();
		this.timeStamp = LocalDateTime.now();
		this.message = message;

	}

	public ErrorResp(String message) {
		super();

		this.message = message;

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return "ErrorResp [message=" + message + ", time=" + timeStamp + "]";
	}

}