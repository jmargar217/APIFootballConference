package com.football.conference.error;

public class PlayerNotInClubException extends RuntimeException {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 733598926549885221L;

	public PlayerNotInClubException(Long clubId, Long playerId) {
		super("Player with id: "+ clubId + " not found on team with id: " + playerId);
	} {

	}
}
