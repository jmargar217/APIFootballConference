package com.football.conference.dto;

public class ClubDetailsResponseDTO {
	private Long id;
	private String officialName;
	private String popularName;
	private String federation;
	private Boolean publicInfo;
	private int numberPlayers;
	
	public ClubDetailsResponseDTO(Long id, String officialName, String popularName, String federation,
			Boolean publicInfo, int numberPlayers) {
		super();
		this.id = id;
		this.officialName = officialName;
		this.popularName = popularName;
		this.federation = federation;
		this.publicInfo = publicInfo;
		this.numberPlayers = numberPlayers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public int getNumberPlayers() {
		return numberPlayers;
	}

	public void setNumberPlayers(int numberPlayers) {
		this.numberPlayers = numberPlayers;
	}
	
}
