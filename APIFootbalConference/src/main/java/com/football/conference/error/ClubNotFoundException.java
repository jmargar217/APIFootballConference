package com.football.conference.error;

public class ClubNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6571817453663089357L;
	
	public ClubNotFoundException(Long clubId) {
		super("Club "+ clubId + " not found");
	}

}
