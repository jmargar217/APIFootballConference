package com.football.conference.dto;

public class ClubResponseDTO {
	private Long id;
	private String officialName;
	private String popularName;
	private String federation;
	private Boolean publicInfo;
	
	public ClubResponseDTO(Long id, String officialName, String popularName, String federation, Boolean publicInfo) {
		super();
		this.id = id;
		this.officialName = officialName;
		this.popularName = popularName;
		this.federation = federation;
		this.publicInfo = publicInfo;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}
