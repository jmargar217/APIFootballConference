package com.football.conference.error;

public class InvalidCredentialException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5524025799761956800L;

	public InvalidCredentialException() {
		super("The data entered is not correct");
	}

}
