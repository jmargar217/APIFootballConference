package com.football.conference.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.football.conference.dto.ClubDetailsResponseDTO;
import com.football.conference.dto.ClubRequestRegisterDTO;
import com.football.conference.dto.ClubResponseDTO;
import com.football.conference.dto.PlayerDetailResponseDTO;
import com.football.conference.dto.PlayerRequestDTO;
import com.football.conference.dto.PlayerResponseDTO;
import com.football.conference.entity.Player;
import com.football.conference.security.JWTUtil;
import com.football.conference.service.ClubServiceDB;
import com.football.conference.service.PlayerServiceDB;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/club")
public class ClubController {
	
	private final ClubServiceDB clubServiceDB;
	private final PlayerServiceDB playerServiceDB;
    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

	
	public ClubController(
			ClubServiceDB clubServiceDB,
			PlayerServiceDB playerServiceDB,
			JWTUtil jwtUtil,
			AuthenticationManager authManager,
			PasswordEncoder passwordEncoder
	) {
		this.clubServiceDB = clubServiceDB;
		this.playerServiceDB = playerServiceDB;
		this.jwtUtil = jwtUtil;
		this.passwordEncoder = passwordEncoder;
	}	
	
    
	/**
	 * Register club
	 * @param club
	 * @return
	 */
	@PostMapping()
	public String register(@Valid @RequestBody ClubRequestRegisterDTO club) {
		String encodedPass = passwordEncoder.encode(club.getPassword()); 
		club.setPassword(encodedPass);
		 
		this.clubServiceDB.addClub(club);
	    String token = jwtUtil.generateToken(club.getUsername());
	    return token;
	}
	
	
	/**
	 * Add player in club
	 * @param clubId
	 * @param player
	 * @return
	 */
	@PostMapping("/{clubId}/player")
	public ResponseEntity<Player> addPlayer(
			@PathVariable Long clubId, @Valid @RequestBody PlayerRequestDTO player) {
		return ResponseEntity.status(HttpStatus.OK).body(this.playerServiceDB.addPlayer(clubId, player));
	}
	
	/**
	 * Get club public
	 * @return
	 */
	@GetMapping
	public ResponseEntity<List<ClubResponseDTO>> getClubPublic() {
		return ResponseEntity.status(HttpStatus.OK).body(this.clubServiceDB.findByPublicInfoTrue());
	}
	
	/**
	 * Get club and number players
	 * @param clubId
	 * @return
	 */
	@GetMapping("/{clubId}")
	public ResponseEntity<ClubDetailsResponseDTO> getClubDetailsWithPlayerCount(@PathVariable Long clubId) {
		return ResponseEntity.status(HttpStatus.OK).body(this.playerServiceDB.getClubDetailsWithPlayerCount(clubId));
	}
	
	
	/**
	 * Get players of club
	 * @param clubId
	 * @return
	 */
	@GetMapping("/{clubId}/player")
	public ResponseEntity<List<PlayerResponseDTO>> getPlayersOfClub(@PathVariable Long clubId) {
		return ResponseEntity.status(HttpStatus.OK).body(this.playerServiceDB.getPlayersOfClub(clubId));
	}
	
	/**
	 * Get player detail of club
	 * @param clubId
	 * @param playerId
	 * @return
	 */
	@GetMapping("/{clubId}/player/{playerId}")
	public ResponseEntity<PlayerDetailResponseDTO> getPlayerOfClub(@PathVariable Long clubId, @PathVariable Long playerId) {
		return ResponseEntity.status(HttpStatus.OK).body(this.playerServiceDB.getPlayerOfClub(clubId, playerId));
	}
	
	/**
	 * Modify club data
	 * @param clubId
	 * @param club
	 * @return
	 */
	@PutMapping("/{clubId}")
	public ResponseEntity<ClubResponseDTO> updateClub(@PathVariable Long clubId, @Valid @RequestBody ClubRequestRegisterDTO club) {
		return ResponseEntity.status(HttpStatus.OK).body(this.clubServiceDB.updateClub(clubId, club));
	}
	
	
	/**
	 * Modify club player data
	 * @param clubId
	 * @param playerId
	 * @param player
	 * @return
	 */
	@PutMapping("/{clubId}/player/{playerId}")
	public ResponseEntity<PlayerDetailResponseDTO> updateClubPlayer(@PathVariable Long clubId, @PathVariable Long playerId, @Valid @RequestBody PlayerRequestDTO player) {
		return ResponseEntity.status(HttpStatus.OK).body(this.playerServiceDB.updateClubPlayer(clubId, playerId, player));
	}
	
	/**
	 * Modify club player data
	 * @param clubId
	 * @param playerId
	 * @param player
	 * @return
	 */
	@DeleteMapping("/{clubId}/player/{playerId}")
	public  ResponseEntity<?> deleteClubPlayer(@PathVariable Long clubId, @PathVariable Long playerId) {
		this.playerServiceDB.deleteClubPlayer(clubId, playerId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
	}
	
	
	
	
	
}
