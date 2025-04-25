package com.football.conference.error;

public class PlayerNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8199881642243419418L;

	public PlayerNotFoundException(Long playerId) {
		super("Player not found with id: " + playerId);
	} {

	}
}
