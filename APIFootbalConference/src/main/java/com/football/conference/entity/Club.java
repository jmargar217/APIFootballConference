package com.football.conference.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "club")
public class Club {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "username", unique=true)
	@NotBlank(message = "Username is required")
	@Email(message = "Must be a valid username") 
	private String username;
	
	@Column(name = "password")
	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Password must have a minimum of 8 characters")
	private String password;
	
	@Column(name = "officialName")
	@NotBlank(message = "OfficialName is required")
	private String officialName;
	
	@Column(name = "popularName")
	@NotBlank(message = "PopularName is required")
	private String popularName;
	
	@Column(name = "federation")
	@NotBlank(message = "Federation is required")
	@Size(max = 8, message = "Federation must have a maximum of 8 characters")
	private String federation;
	
	@Column(name = "public")
	@NotNull(message = "PublicInfo is required")
	private Boolean publicInfo;
	
	@OneToMany(mappedBy = "club", cascade =  CascadeType.PERSIST, orphanRemoval = true)
	private List<Player>players = new ArrayList<>();
	

	public Club() {
		super();
	}
	
	public Club(String username, String password, String officialName, String popularName, String federation,
			Boolean publicInfo) {
		super();
		this.username = username;
		this.password = password;
		this.officialName = officialName;
		this.popularName = popularName;
		this.federation = federation;
		this.publicInfo = publicInfo;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public boolean isPublicInfo() {
		return publicInfo;
	}

	public void setPublicInfo(boolean publicInfo) {
		this.publicInfo = publicInfo;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}
}
