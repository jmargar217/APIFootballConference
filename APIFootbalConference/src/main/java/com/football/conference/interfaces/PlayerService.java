package com.football.conference.interfaces;

import java.util.List;

import com.football.conference.dto.ClubDetailsResponseDTO;
import com.football.conference.dto.PlayerDetailResponseDTO;
import com.football.conference.dto.PlayerRequestDTO;
import com.football.conference.dto.PlayerResponseDTO;
import com.football.conference.entity.Player;


public interface PlayerService {
	
	
	public Player addPlayer (Long clubId, PlayerRequestDTO player);
	
	public ClubDetailsResponseDTO getClubDetailsWithPlayerCount(Long id);
	
	public List<PlayerResponseDTO> getPlayersOfClub(Long clubId);
	
	public PlayerDetailResponseDTO getPlayerOfClub(Long clubId, Long playerId);

	public PlayerDetailResponseDTO updateClubPlayer(Long clubId, Long playerId, PlayerRequestDTO player);
	
	public void deleteClubPlayer(Long clubId, Long playerId);
}
