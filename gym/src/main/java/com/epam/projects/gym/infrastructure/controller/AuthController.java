package com.epam.projects.gym.infrastructure.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.projects.gym.application.dto.request.ChangeLogin;
import com.epam.projects.gym.application.dto.request.UserLogin;
import com.epam.projects.gym.application.service.TraineeService;
import com.epam.projects.gym.application.service.TrainerService;
import com.epam.projects.gym.domain.exception.NotFoundException;
import com.epam.projects.gym.infrastructure.service.AuthService;
import com.epam.projects.gym.infrastructure.service.JwtService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/auth")
@Api(tags = "Auth")
public class AuthController {
	
	private TraineeService traineeService;
	
	private TrainerService trainerService;
	
	private AuthService authService;
	
	private AuthenticationManager authenticationManager;
	
	private JwtService tokenManager;
	
	public AuthController(
			TraineeService traineeService,
			TrainerService trainerService,
			AuthService authService,
			AuthenticationManager authenticationManager,
			JwtService tokenManager) {
		this.traineeService = traineeService;
		this.trainerService = trainerService;
		this.authService = authService;
		this.authenticationManager = authenticationManager;
		this.tokenManager = tokenManager;
	}

	@GetMapping
	@ApiOperation(value = "Logs in to an user.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "User Logged successfully."),
            @ApiResponse(code = 401, message = "Login failed, invalid credentials.")
    })
	public ResponseEntity<Object> login(@ModelAttribute @Valid UserLogin user) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
						user.getUsername(),
						user.getPassword()
					));
		} catch (DisabledException exception) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
		} catch (BadCredentialsException exception) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
		} catch (Exception exception) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
		}
		
		final UserDetails userDetails = authService.loadUserByUsername(user.getUsername());
		final String jwtToken = tokenManager.generateJwtToken(userDetails);
		return ResponseEntity.status(HttpStatus.OK).header("Authorization", "Bearer " + jwtToken).body("Logged sucessfully.");
    }
	
	@PutMapping("/trainee")
	@ApiOperation(value = "Changes a trainee's password.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Trainee password changed successfully."),
            @ApiResponse(code = 401, message = "Change failed, invalid credentials.")
    })
	public ResponseEntity<Object> changeLoginTrainee(@Valid @RequestBody ChangeLogin user) {
		try {
			traineeService.changeTraineePassword(
					user.getUsername(), user.getPassword(), user.getNewPassword());
			return ResponseEntity.status(HttpStatus.OK).body("Password updated sucessfully.");
		} catch (NotFoundException exception) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
		} catch (Exception exception) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
		}
    }
	
	@PutMapping("/trainer")
	@ApiOperation(value = "Changes a trainer's password.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Trainer password changed successfully."),
            @ApiResponse(code = 401, message = "Change failed, invalid credentials.")
    })
	public ResponseEntity<Void> changeLoginTrainer(@Valid @RequestBody ChangeLogin user) {
		boolean isChanged = trainerService.changeTrainerPassword(
				user.getUsername(), user.getPassword(), user.getNewPassword());
		if (isChanged) {
			return ResponseEntity.status(200).build();
		} else {
			return ResponseEntity.status(401).build();
		}
    }

}
