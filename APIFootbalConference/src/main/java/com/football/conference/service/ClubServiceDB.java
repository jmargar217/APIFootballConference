package com.football.conference.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.football.conference.dto.ClubRequestRegisterDTO;
import com.football.conference.dto.ClubResponseDTO;
import com.football.conference.entity.Club;
import com.football.conference.error.ClubDuplicateException;
import com.football.conference.error.ClubNotFoundException;
import com.football.conference.interfaces.ClubService;
import com.football.conference.repository.ClubRepository;

import jakarta.transaction.Transactional;

@Service
public class ClubServiceDB implements ClubService {
	
    private final ClubRepository clubRepository;
    private final PasswordEncoder passwordEncoder;

    public ClubServiceDB(ClubRepository clubRepository, PasswordEncoder passwordEncoder) {
        this.clubRepository = clubRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
	@Override
	public Optional<Club> findByUsername(String username) {
		return clubRepository.findByUsername(username);
	}

	@Override
	@Transactional
	public Club addClub(ClubRequestRegisterDTO club) {
		
	    clubRepository.findByUsername(club.getUsername())
        .ifPresent(existingClub -> {
            throw new ClubDuplicateException(club.getUsername());
        });
		
		Club clubToSave = new Club(
				club.getUsername(), club.getPassword(), club.getOfficialName(),
				club.getPopularName(), club.getFederation(), club.getPublicInfo());
	   
		return clubRepository.save(clubToSave);
	}

	@Override
	public List<ClubResponseDTO> findByPublicInfoTrue() {
		List<Club> clubs = new ArrayList<>(clubRepository.findByPublicInfoTrue());
		
		return clubs.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
	}
	
	
    private ClubResponseDTO convertToDto(Club club) {
        return new ClubResponseDTO(
        		club.getId(),
                club.getOfficialName(),
                club.getPopularName(),
                club.getFederation(),
                club.isPublicInfo()
        );
    }

	@Override
	public Optional<Club> findById(Long id) {
		return clubRepository.findById(id);
	}

	@Override
	@Transactional
	public ClubResponseDTO updateClub(Long id, ClubRequestRegisterDTO clubDTO) {
	    Club clubToUpdate = clubRepository.findById(id)
	            .orElseThrow(() -> new ClubNotFoundException(id));
	    
	    if (!clubToUpdate.getUsername().equals(clubDTO.getUsername())) {
	    	Optional<Club> userRes = clubRepository.findByUsername(clubDTO.getUsername());
	        if (userRes.isPresent()) {
	            throw new ClubDuplicateException(clubDTO.getUsername());
	        }
	        clubToUpdate.setUsername(clubDTO.getUsername());
	    }
	    
	    clubToUpdate.setOfficialName(clubDTO.getOfficialName());
	    clubToUpdate.setPopularName(clubDTO.getPopularName());
	    clubToUpdate.setFederation(clubDTO.getFederation());
	    clubToUpdate.setPublicInfo(clubDTO.getPublicInfo());
	    
	    if (!passwordEncoder.matches(clubDTO.getPassword(), clubToUpdate.getPassword())) {
	        clubToUpdate.setPassword(passwordEncoder.encode(clubDTO.getPassword()));
	    }    
	    
	    return new ClubResponseDTO(
	            clubToUpdate.getId(),
	            clubToUpdate.getOfficialName(),
	            clubToUpdate.getPopularName(),
	            clubToUpdate.getFederation(),
	            clubToUpdate.isPublicInfo()
	    );
	}
	
}
