package com.football.conference.interfaces;

import java.util.List;
import java.util.Optional;

import com.football.conference.dto.ClubRequestRegisterDTO;
import com.football.conference.dto.ClubResponseDTO;
import com.football.conference.entity.Club;

public interface ClubService {
	
	public Optional<Club> findByUsername(String username);
	
	public Club addClub(ClubRequestRegisterDTO club);
	public List<ClubResponseDTO> findByPublicInfoTrue();
	
	public Optional<Club> findById(Long id);
	
	public ClubResponseDTO updateClub(Long id, ClubRequestRegisterDTO club);
	

}
