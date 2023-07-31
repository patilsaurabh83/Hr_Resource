package com.cg.hrresource.exception;

import java.time.LocalDate;

public class InvalidEmployeeException extends Exception {
	
	public InvalidEmployeeException(LocalDate timestamp, String str) {
		super();
	}

}
