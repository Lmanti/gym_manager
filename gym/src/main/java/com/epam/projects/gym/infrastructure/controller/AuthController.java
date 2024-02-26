package com.epam.projects.gym.infrastructure.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.projects.gym.application.dto.request.ChangeLogin;
import com.epam.projects.gym.application.dto.request.UserLogin;
import com.epam.projects.gym.application.service.TraineeService;
import com.epam.projects.gym.application.service.TrainerService;
import com.epam.projects.gym.domain.exception.NotMatchException;
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
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
					user.getUsername(),
					user.getPassword()
				));
		
		final UserDetails userDetails = authService.loadUserByUsername(user.getUsername());
		final String jwtToken = tokenManager.generateJwtToken(userDetails);
		return ResponseEntity.status(HttpStatus.OK).header("Authorization", "Bearer " + jwtToken).body("Logged in sucessfully.");
    }
	
	@GetMapping("/logout")
	@ApiOperation(value = "Logs in to an user.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "User Logged successfully."),
            @ApiResponse(code = 401, message = "Login failed, invalid credentials.")
    })
	public ResponseEntity<Object> logout(@RequestParam String token) {
		tokenManager.revokeToken(token);
		return ResponseEntity.status(HttpStatus.OK).body("Logged out sucessfully.");
    }
	
	@PutMapping("/trainee")
	@ApiOperation(value = "Changes a trainee's password.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Trainee password changed successfully."),
            @ApiResponse(code = 401, message = "Change failed, invalid credentials.")
    })
	public ResponseEntity<Object> changeLoginTrainee(@RequestBody @Valid ChangeLogin user) {
		traineeService.changeTraineePassword(
				user.getUsername(), user.getPassword(), user.getNewPassword());
		return ResponseEntity.status(HttpStatus.OK).body("Password updated sucessfully.");
    }
	
	@PutMapping("/trainer")
	@ApiOperation(value = "Changes a trainer's password.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Trainer password changed successfully."),
            @ApiResponse(code = 401, message = "Change failed, invalid credentials.")
    })
	public ResponseEntity<Object> changeLoginTrainer(@RequestBody @Valid ChangeLogin user) {
		trainerService.changeTrainerPassword(
				user.getUsername(), user.getPassword(), user.getNewPassword());
		return ResponseEntity.status(HttpStatus.OK).body("Password updated sucessfully.");
    }
	
	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<Object> disabledExceptionHandler(DisabledException exception) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
	}
	
	@ExceptionHandler({BadCredentialsException.class, NotMatchException.class})
	public ResponseEntity<Object> badCredentialsExceptionHandler(Exception exception) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
	}

}
