package com.football.conference.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.football.conference.dto.PlayerDetailResponseDTO;
import com.football.conference.dto.PlayerResponseDTO;
import com.football.conference.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
	
	  	@Query("SELECT COUNT(p) FROM Player p WHERE p.club.id = :clubId")
	    int countPlayersByClubId(@Param("clubId") Long clubId);
	  
	  
	    @Query("SELECT new com.football.conference.dto.PlayerResponseDTO(p.id, p.givenName, p.familyName) FROM Player p WHERE p.club.id = :clubId")
	    List<PlayerResponseDTO> findPlayersDtoByClubId(@Param("clubId") Long clubId);
	    
	    @Query("SELECT new com.football.conference.dto.PlayerDetailResponseDTO(p.id, p.givenName, p.familyName, p.nationality, p.email, p.dateOfBirth)" +
	           "FROM Player p WHERE p.club.id = :clubId AND p.id = :playerId")
	    Optional<PlayerDetailResponseDTO> findPlayerDtoByClubAndPlayerId(
	            @Param("clubId") Long clubId, 
	            @Param("playerId") Long playerId);


		Optional<Player> findByEmail(String email);


		@Query("SELECT p FROM Player p LEFT JOIN FETCH p.club WHERE p.id = :playerId")
		Optional<Player> findByIdWithClub(@Param("playerId") Long playerId);
}
