package com.football.conference.dto;


public class PlayerResponseDTO {
	
	private Long id;
	
	private String givenName;
	
	private String familyName;

	public PlayerResponseDTO(Long id, String givenName, String familyName) {
		super();
		this.id = id;
		this.givenName = givenName;
		this.familyName = familyName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	
	
	
	
}
