package com.football.conference.controller;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.football.conference.dto.ClubRequestDTO;
import com.football.conference.entity.Club;
import com.football.conference.error.InvalidCredentialException;
import com.football.conference.security.JWTUtil;
import com.football.conference.service.ClubServiceDB;

@RestController
@RequestMapping("/login")
public class AuthController {
	
	private final ClubServiceDB clubServiceDB;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authManager;
    
    public AuthController (
    		ClubServiceDB clubServiceDB, JWTUtil jwtUtil, AuthenticationManager authManager) {
    	this.clubServiceDB = clubServiceDB;
		this.jwtUtil = jwtUtil;
		this.authManager = authManager;
    }
	
	@PostMapping
	public String login(@RequestBody ClubRequestDTO club) {
		String response = "";
        try {
        	String username = club.getUsername();
            UsernamePasswordAuthenticationToken authInputToken =
                    new UsernamePasswordAuthenticationToken(username, club.getPassword());
            
            authManager.authenticate(authInputToken);
            Optional<Club> clubFound =  this.clubServiceDB.findByUsername(username);
            if (clubFound.get() != null) {
            	response  = jwtUtil.generateToken(username);
            }
        }catch (Exception e){
            throw new InvalidCredentialException();
        }
		return response;
    }

}
