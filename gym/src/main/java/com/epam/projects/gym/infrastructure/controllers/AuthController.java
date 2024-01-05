package com.epam.projects.gym.infrastructure.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.projects.gym.application.dto.requests.ChangeLogin;
import com.epam.projects.gym.application.dto.requests.UserLogin;
import com.epam.projects.gym.application.service.AuthService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/api/auth")
@Api(tags = "Auth")
public class AuthController {
	
	@Autowired
	private AuthService facade;

	@PostMapping
	@ApiOperation(value = "Log in to an user.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "User Logged successfully."),
            @ApiResponse(code = 400, message = "Login failed, please check the info.")
    })
	public ResponseEntity<Void> login(@RequestBody UserLogin user) {
		boolean newTrainee = facade.login(user);
		if (newTrainee) {
			return ResponseEntity.status(200).build();
		} else {
			return ResponseEntity.status(400).build();
		}
    }
	
	@PutMapping
	@ApiOperation(value = "Change an user's password.")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "User password changed successfully."),
            @ApiResponse(code = 400, message = "Change failed, please check the info.")
    })
	public ResponseEntity<Void> changeLogin(@RequestBody ChangeLogin user) {
		boolean newTrainee = facade.changeLogin(user);
		if (newTrainee) {
			return ResponseEntity.status(200).build();
		} else {
			return ResponseEntity.status(400).build();
		}
    }

}
