package com.football.conference.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.football.conference.error.ApiError;
import com.football.conference.error.ClubDuplicateException;
import com.football.conference.error.ClubNotFoundException;
import com.football.conference.error.InvalidCredentialException;
import com.football.conference.error.PlayerAlreadyInTeamException;
import com.football.conference.error.PlayerDuplicateException;
import com.football.conference.error.PlayerNotFoundException;
import com.football.conference.error.PlayerNotInClubException;

@ControllerAdvice
public class ExceptionHandlerController {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
	 
    @ExceptionHandler(ClubDuplicateException.class)
	public ResponseEntity<ApiError> handleUserDuplicate(ClubDuplicateException ex) {
		ApiError apiError = new ApiError();
		apiError.setStatus(HttpStatus.CONFLICT);
		apiError.setDate(LocalDateTime.now());
		apiError.setMessage(ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
	}
    
	 @ExceptionHandler(InvalidCredentialException.class)
	   	public ResponseEntity<ApiError> handleInvalidLogin(InvalidCredentialException ex) {
	   		ApiError apiError = new ApiError();
	   		apiError.setStatus(HttpStatus.UNAUTHORIZED);
	   		apiError.setDate(LocalDateTime.now());
	   		apiError.setMessage(ex.getMessage());
	   		
	   		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
	 }
	 
	 @ExceptionHandler(ClubNotFoundException.class)
	 public ResponseEntity<ApiError> handleClubIdNotFound(ClubNotFoundException ex) {
			ApiError apiError = new ApiError();
			apiError.setStatus(HttpStatus.NOT_FOUND);
			apiError.setDate(LocalDateTime.now());
			apiError.setMessage(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	 }
	 
	 @ExceptionHandler(PlayerAlreadyInTeamException.class)
	 public ResponseEntity<ApiError> handlePlayerAlreadyInTeam(PlayerAlreadyInTeamException ex) {
			ApiError apiError = new ApiError();
			apiError.setStatus(HttpStatus.CONFLICT);
			apiError.setDate(LocalDateTime.now());
			apiError.setMessage(ex.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
	 }
	 
	 @ExceptionHandler(PlayerNotFoundException.class)
	 public ResponseEntity<ApiError> handlePlayerNotFound(PlayerNotFoundException ex) {
			ApiError apiError = new ApiError();
			apiError.setStatus(HttpStatus.NOT_FOUND);
			apiError.setDate(LocalDateTime.now());
			apiError.setMessage(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	 }
	 
	 @ExceptionHandler(PlayerDuplicateException.class)
	 public ResponseEntity<ApiError> handlePlayerDuplicate(PlayerDuplicateException ex) {
			ApiError apiError = new ApiError();
			apiError.setStatus(HttpStatus.NOT_FOUND);
			apiError.setDate(LocalDateTime.now());
			apiError.setMessage(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	 }
	 
	 @ExceptionHandler(PlayerNotInClubException.class)
	 public ResponseEntity<ApiError> handlePlayerNotInClub(PlayerNotInClubException ex) {
			ApiError apiError = new ApiError();
			apiError.setStatus(HttpStatus.NOT_FOUND);
			apiError.setDate(LocalDateTime.now());
			apiError.setMessage(ex.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	 }
}
