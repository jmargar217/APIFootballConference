package com.football.conference.dto;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public class PlayerRequestDTO {
	
	@NotBlank(message = "givenName is required")
	private String givenName;
	
	@NotBlank(message = "familyName is required")
	private String familyName;
	
	@NotBlank(message = "nationality is required")
	private String nationality;
	
	@NotBlank(message = "email is required")
	private String email;
	
	@NotNull(message = "dateOfBirth is required")
	@Past(message = "dateOfBirth must be a past date")
	private Date dateOfBirth;

	public PlayerRequestDTO(@NotBlank(message = "givenName is required") String givenName,
			@NotBlank(message = "familyName is required") String familyName,
			@NotBlank(message = "nationality is required") String nationality,
			@NotBlank(message = "email is required") String email,
			@NotNull(message = "dateOfBirth is required") @Past(message = "dateOfBirth must be a past date") Date dateOfBirth) {
		super();
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

}
