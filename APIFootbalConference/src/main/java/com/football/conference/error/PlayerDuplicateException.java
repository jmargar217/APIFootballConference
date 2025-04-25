package com.football.conference.error;

public class PlayerDuplicateException extends RuntimeException {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1540718448270543678L;

	public PlayerDuplicateException(String email) {
		super("Already registered : "+ email);
	}

}
