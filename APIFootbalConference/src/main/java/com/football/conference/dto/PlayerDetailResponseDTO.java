package com.football.conference.dto;

import java.util.Date;


public class PlayerDetailResponseDTO {
	
	private Long id;
	
	private String givenName;

	private String familyName;
	
	private String nationality;
	
	private String email;
	
	private Date dateOfBirth;

	public PlayerDetailResponseDTO(Long id, String givenName, String familyName, String nationality, String email,
			Date dateOfBirth) {
		super();
		this.id = id;
		this.givenName = givenName;
		this.familyName = familyName;
		this.nationality = nationality;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
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

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
