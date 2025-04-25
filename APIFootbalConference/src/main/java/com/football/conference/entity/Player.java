package com.football.conference.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

@Entity
@Table(name = "player")
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "givenName")
	@NotBlank(message = "givenName is required")
	private String givenName;
	
	@Column(name = "familyName")
	@NotBlank(message = "familyName is required")
	private String familyName;
	
	@Column(name = "nationality")
	@NotBlank(message = "nationality is required")
	private String nationality;
	
	@Column(name = "email", unique=true)
	@NotBlank(message = "email is required")
	@Email(message = "Must be a valid email") 
	private String email;
	
	@Column(name = "dateOfBirth")
	@NotNull(message = "dateOfBirth is required")
	@Past(message = "dateOfBirth must be a past date")
	private Date dateOfBirth;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "club_id")
	@JsonIgnore
    private Club club;
	
	public Player() {
		super();
	}
	
	public Player(String givenName, String familyName, String nationality, String email, Date dateOfBirth) {
		super();
		this.givenName = givenName;
		this.familyName = familyName;
		this.nationality = nationality;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
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

	public Club getClub() {
		return club;
	}

	public void setClub(Club club) {
		this.club = club;
	}
	
	
	
}
