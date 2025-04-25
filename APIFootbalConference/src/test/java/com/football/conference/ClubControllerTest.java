package com.football.conference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.football.conference.controller.ClubController;
import com.football.conference.dto.ClubDetailsResponseDTO;
import com.football.conference.dto.ClubRequestRegisterDTO;
import com.football.conference.entity.Club;
import com.football.conference.error.ClubDuplicateException;
import com.football.conference.error.ClubNotFoundException;
import com.football.conference.security.JWTUtil;
import com.football.conference.service.ClubServiceDB;
import com.football.conference.service.PlayerServiceDB;

@ExtendWith(MockitoExtension.class)
class ClubControllerTest {
	
	private static final String TEST_EMAIL = "testuser@email.com";
	private static final String TEST_PASSWORD = "12345678";
	
	@Mock
	private ClubServiceDB clubServiceDB;
	    
	@Mock
	private PlayerServiceDB playerServiceDB;
	    
	@Mock
	private JWTUtil jwtUtil;
	    
	@Mock
	private PasswordEncoder passwordEncoder;
	    
	@InjectMocks
	private ClubController clubController;

	private ClubRequestRegisterDTO clubRequest;
	private Club clubEntity;
	private ClubDetailsResponseDTO clubDetailsResponse;
	
	
	@BeforeEach
    void setUp() {
        clubRequest = new ClubRequestRegisterDTO(
        		TEST_EMAIL, TEST_PASSWORD, "Official FC", 
            "Test FC", "UEFA", true
        );
        
        clubEntity = new Club(
        		TEST_EMAIL, TEST_PASSWORD, "Official FC", 
            "Test FC", "UEFA", true
        );  
        
        clubDetailsResponse = new ClubDetailsResponseDTO(
            1L, "Official FC", "Test FC", "UEFA", true, 5
        );
	}
	
	@Test
	void registerClub_ShouldReturnToken_WhenRegistrationSuccessful() {
		when(passwordEncoder.encode(eq(TEST_PASSWORD))).thenReturn("$2a$10$fakeHashedPassword");
	    when(clubServiceDB.addClub(eq(clubRequest))).thenReturn(clubEntity);
	    when(jwtUtil.generateToken(anyString())).thenReturn("generated.jwt.token");
	    
	    String result = clubController.register(clubRequest);
	    
	    assertEquals("generated.jwt.token", result);
	    verify(passwordEncoder).encode(TEST_PASSWORD);
	    verify(clubServiceDB).addClub(clubRequest);
	    verify(jwtUtil).generateToken(TEST_EMAIL);
	}
	
	@Test
	void registerClub_ShouldThrowException_WhenUsernameExists() {
		when(passwordEncoder.encode(eq(TEST_PASSWORD))).thenReturn("$2a$10$fakeHashedPassword");
		when(clubServiceDB.addClub(eq(clubRequest))).thenThrow(new ClubDuplicateException(TEST_EMAIL));
	    
	    ClubDuplicateException exception = assertThrows(ClubDuplicateException.class, 
	        () -> clubController.register(clubRequest));
	    
	    assertEquals("Already registered : "+ TEST_EMAIL, exception.getMessage());
	    verify(passwordEncoder).encode(TEST_PASSWORD);
	}
	
	@Test
	void getClubDetails_ShouldReturnClubDetails_WhenClubExists() {
	    when(playerServiceDB.getClubDetailsWithPlayerCount(1L)).thenReturn(clubDetailsResponse);
	    
	    ResponseEntity<ClubDetailsResponseDTO> response = clubController.getClubDetailsWithPlayerCount(1L);
	    
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    assertNotNull(response.getBody());
	    assertEquals(1L, response.getBody().getId());
	    assertEquals("Official FC", response.getBody().getOfficialName());
	    assertEquals(5, response.getBody().getNumberPlayers());
	    verify(playerServiceDB).getClubDetailsWithPlayerCount(1L);
	}
	
	
	@Test
	void getClubDetails_ShouldThrowException_WhenClubNotFound() {
	    when(playerServiceDB.getClubDetailsWithPlayerCount(99L))
	        .thenThrow(new ClubNotFoundException(99L));
	    
	    ClubNotFoundException exception = assertThrows(ClubNotFoundException.class, 
	        () -> clubController.getClubDetailsWithPlayerCount(99L));
	    
	    assertTrue(exception.getMessage().contains("99"));
	    verify(playerServiceDB).getClubDetailsWithPlayerCount(99L);
	}
}
