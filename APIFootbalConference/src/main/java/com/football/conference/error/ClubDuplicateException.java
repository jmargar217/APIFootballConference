package com.football.conference.error;

public class ClubDuplicateException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4942265603786384133L;

	public ClubDuplicateException(String username) {
		super("Already registered : "+ username);
	}

}
