package com.football.conference.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ClubRequestRegisterDTO {
	
	@NotBlank(message = "Username is required")
	@Email(message = "Must be a valid username") 
	private String username;
	
	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Password must have a minimum of 8 characters")
	private String password;
	
	@NotBlank(message = "OfficialName is required")
	private String officialName;
	
	@NotBlank(message = "PopularName is required")
	private String popularName;
	
	@NotBlank(message = "Federation is required")
	@Size(max = 8, message = "Federation must have a maximum of 8 characters")
	private String federation;
	
	@NotNull(message = "PublicInfo is required")
	private Boolean publicInfo;
	

	public ClubRequestRegisterDTO(
			@NotBlank(message = "Username is required") @Email(message = "Must be a valid username") String username,
			@NotBlank(message = "Password is required") @Size(min = 8, message = "Password must have a minimum of 8 characters") String password,
			@NotBlank(message = "OfficialName is required") String officialName,
			@NotBlank(message = "PopularName is required") String popularName,
			@NotBlank(message = "Federation is required") @Size(max = 8, message = "Federation must have a maximum of 8 characters") String federation,
			@NotNull(message = "PublicInfo is required") Boolean publicInfo) {
		super();
		this.username = username;
		this.password = password;
		this.officialName = officialName;
		this.popularName = popularName;
		this.federation = federation;
		this.publicInfo = publicInfo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOfficialName() {
		return officialName;
	}

	public void setOfficialName(String officialName) {
		this.officialName = officialName;
	}

	public String getPopularName() {
		return popularName;
	}

	public void setPopularName(String popularName) {
		this.popularName = popularName;
	}

	public String getFederation() {
		return federation;
	}

	public void setFederation(String federation) {
		this.federation = federation;
	}

	public Boolean getPublicInfo() {
		return publicInfo;
	}

	public void setPublicInfo(Boolean publicInfo) {
		this.publicInfo = publicInfo;
	}
	
	
}
