package com.football.conference.error;

public class PlayerAlreadyInTeamException extends RuntimeException {
	private static final long serialVersionUID = -4754135622071388903L;
	
	public PlayerAlreadyInTeamException(String playerEmail) {
		super("Player with email '" + playerEmail + "' is already in the team");
	}
}
