package com.football.conference.security;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.football.conference.entity.Club;
import com.football.conference.repository.ClubRepository;

@Component
public class MyClubDetailsService implements UserDetailsService {

    @Autowired private ClubRepository clubRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Club> userRes = clubRepository.findByUsername(username);
        if(userRes.isEmpty())
            throw new UsernameNotFoundException("Could not findUser with username = " + username);
        Club club = userRes.get();
        return new org.springframework.security.core.userdetails.User(
        		username,
                club.getPassword(), 
                Collections.singletonList(new SimpleGrantedAuthority("CLUB")));
    }
}