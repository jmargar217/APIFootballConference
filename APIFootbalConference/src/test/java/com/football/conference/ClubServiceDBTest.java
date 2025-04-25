package com.football.conference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.football.conference.dto.ClubRequestRegisterDTO;
import com.football.conference.dto.ClubResponseDTO;
import com.football.conference.entity.Club;
import com.football.conference.error.ClubDuplicateException;
import com.football.conference.repository.ClubRepository;
import com.football.conference.service.ClubServiceDB;

@ExtendWith(MockitoExtension.class)
class ClubServiceDBTest {
    private static final String TEST_EMAIL = "testuser@email.com";
    private static final String TEST_PASSWORD = "12345678";
    
    @Mock
    private ClubRepository clubRepository;
        
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private ClubServiceDB clubServiceDB;

    private ClubRequestRegisterDTO clubRequest;
    
    @BeforeEach
    void setUp() {
        clubRequest = new ClubRequestRegisterDTO(
            TEST_EMAIL, 
            TEST_PASSWORD, 
            "Official FC", 
            "Test FC", 
            "UEFA", 
            true
        );
    }
    
    @Test
    void addClub_ShouldThrowException_WhenUsernameExists() {
        Club existingClub = new Club();
        when(clubRepository.findByUsername(TEST_EMAIL)).thenReturn(Optional.of(existingClub));
        
        ClubDuplicateException exception = assertThrows(ClubDuplicateException.class, () -> {
            clubServiceDB.addClub(clubRequest);
        });
        
        assertEquals("Already registered : " + TEST_EMAIL, exception.getMessage());
        verify(clubRepository).findByUsername(TEST_EMAIL);
        verify(passwordEncoder, never()).encode(toString());
        verify(clubRepository, never()).save(any(Club.class));
    }
    
    @Test
    void updateClub_ShouldUpdateClub_WhenDataIsValid() {
        Long clubId = 1L;
        Club existingClub = new Club(
            "olduser@email.com", 
            "oldEncodedPassword", 
            "Old Official", 
            "Old Popular", 
            "Old Fed", 
            false
        );
        existingClub.setId(clubId);
        
        when(clubRepository.findById(clubId)).thenReturn(Optional.of(existingClub));
        when(passwordEncoder.matches(TEST_PASSWORD, "oldEncodedPassword")).thenReturn(false);
        when(passwordEncoder.encode(TEST_PASSWORD)).thenReturn("newEncodedPassword");
        
        ClubResponseDTO result = clubServiceDB.updateClub(clubId, clubRequest);
        
        assertNotNull(result);
        assertEquals(clubId, result.getId());
        assertEquals("Official FC", result.getOfficialName());
        assertEquals("Test FC", result.getPopularName());
        assertEquals("UEFA", result.getFederation());
        assertTrue(result.getPublicInfo());
        
        verify(clubRepository).findById(clubId);
        verify(passwordEncoder).matches(TEST_PASSWORD, "oldEncodedPassword");
        verify(passwordEncoder).encode(TEST_PASSWORD);
    }
}
