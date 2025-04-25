package com.football.conference.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.football.conference.dto.ClubDetailsResponseDTO;
import com.football.conference.dto.PlayerDetailResponseDTO;
import com.football.conference.dto.PlayerRequestDTO;
import com.football.conference.dto.PlayerResponseDTO;
import com.football.conference.entity.Club;
import com.football.conference.entity.Player;
import com.football.conference.error.ClubNotFoundException;
import com.football.conference.error.PlayerAlreadyInTeamException;
import com.football.conference.error.PlayerDuplicateException;
import com.football.conference.error.PlayerNotFoundException;
import com.football.conference.error.PlayerNotInClubException;
import com.football.conference.interfaces.PlayerService;
import com.football.conference.repository.ClubRepository;
import com.football.conference.repository.PlayerRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@Service
public class PlayerServiceDB implements PlayerService {
	
	private final ClubRepository clubRepository;
    private final PlayerRepository playerRepository;

    public PlayerServiceDB(ClubRepository clubRepository, PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
        this.clubRepository = clubRepository;
    }
	
	

    @Override
    @Transactional  
    public Player addPlayer(Long clubId, PlayerRequestDTO player) {
        Club club = this.getClub(clubId);
        
        boolean playerExists = club.getPlayers().stream()
            .anyMatch(p -> p.getEmail().equalsIgnoreCase(player.getEmail()));
        
        if (playerExists) {
            throw new PlayerAlreadyInTeamException(player.getEmail());
        }
      
        Player newPlayer = new Player(
        		player.getGivenName(),
        		player.getFamilyName(),
        		player.getNationality(),
        		player.getEmail(),
        		player.getDateOfBirth()
        );
        
        newPlayer.setClub(club);
        club.getPlayers().add(newPlayer);
        return playerRepository.save(newPlayer);
    }
    
	@Override
	public ClubDetailsResponseDTO getClubDetailsWithPlayerCount(Long clubId) {
		
		Club club = this.getClub(clubId);
		
	    int playerCount = playerRepository.countPlayersByClubId(clubId);
	
	    return new ClubDetailsResponseDTO(
	    		club.getId(),
	            club.getOfficialName(),
	            club.getPopularName(),
	            club.getFederation(),
	            club.isPublicInfo(),
	            playerCount
	     );
	}

	@Override
	public List<PlayerResponseDTO> getPlayersOfClub(Long clubId) {
		return playerRepository.findPlayersDtoByClubId(clubId);
	}
	
	@Override
	public PlayerDetailResponseDTO getPlayerOfClub(Long clubId, Long playerId) {
		PlayerDetailResponseDTO playerResponse = playerRepository.findPlayerDtoByClubAndPlayerId(clubId, playerId)
				.orElseThrow(() -> new PlayerNotFoundException(playerId));
		return playerResponse;
	}
	
	@Override
	@Transactional
	public PlayerDetailResponseDTO updateClubPlayer(Long clubId, Long playerId, @Valid PlayerRequestDTO playerDTO) {
	    Player playerToUpdate = playerRepository.findByIdWithClub(playerId)
	            .orElseThrow(() -> new PlayerNotFoundException(playerId));	    
	    
	    if (playerToUpdate.getClub() == null || !playerToUpdate.getClub().getId().equals(clubId)) {
	        throw new PlayerNotInClubException(playerId, clubId);
	    }

	    if (!playerToUpdate.getEmail().equals(playerDTO.getEmail())) {
	        playerRepository.findByEmail(playerDTO.getEmail())
	                .ifPresent(p -> { throw new PlayerDuplicateException(playerDTO.getEmail()); });
	        playerToUpdate.setEmail(playerDTO.getEmail());
	    }

	    playerToUpdate.setGivenName(playerDTO.getGivenName());
	    playerToUpdate.setFamilyName(playerDTO.getFamilyName());
	    playerToUpdate.setNationality(playerDTO.getNationality());
	    playerToUpdate.setDateOfBirth(playerDTO.getDateOfBirth());

	    return new PlayerDetailResponseDTO(
	            playerToUpdate.getId(),
	            playerToUpdate.getGivenName(),
	            playerToUpdate.getFamilyName(),
	            playerToUpdate.getNationality(),
	            playerToUpdate.getEmail(),
	            playerToUpdate.getDateOfBirth()
	    );
	}
	
	@Override
	@Transactional
	public void deleteClubPlayer(Long clubId, Long playerId) {
	    Club club = clubRepository.findById(clubId)
	            .orElseThrow(() -> new ClubNotFoundException(clubId));
	    
	    Player playerToDelete = club.getPlayers().stream()
	            .filter(p -> p.getId().equals(playerId))
	            .findFirst()
	            .orElseThrow(() -> new PlayerNotInClubException(playerId, clubId));
	    
	    club.getPlayers().remove(playerToDelete);
	}
	
	
	private Club getClub(Long clubId) {
		return this.clubRepository.findById(clubId)
	            .orElseThrow(() -> new ClubNotFoundException(clubId));
	}
	
}
