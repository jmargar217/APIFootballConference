package com.football.conference.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.football.conference.entity.Club;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {
	Optional<Club> findByUsername(String username);
	
	List<Club> findByPublicInfoTrue();
	
	Optional<Club> findById(Long id);

}
