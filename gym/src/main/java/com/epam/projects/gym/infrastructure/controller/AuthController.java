package com.epam.projects.gym.infrastructure.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/auth")
@Api(tags = "Auth")
public class AuthController {
	
//	@Autowired
//	private AuthFacade facade;
//
//	@PostMapping
//	@ApiOperation(value = "Log in to an user.")
//	@ApiResponses(value = {
//            @ApiResponse(code = 200, message = "User Logged successfully."),
//            @ApiResponse(code = 400, message = "Login failed, please check the info.")
//    })
//	public ResponseEntity<Void> login(@RequestBody UserLogin user) {
//		boolean newTrainee = facade.login(user);
//		if (newTrainee) {
//			return ResponseEntity.status(200).build();
//		} else {
//			return ResponseEntity.status(400).build();
//		}
//    }
//	
//	@PutMapping
//	@ApiOperation(value = "Change an user's password.")
//	@ApiResponses(value = {
//            @ApiResponse(code = 200, message = "User password changed successfully."),
//            @ApiResponse(code = 400, message = "Change failed, please check the info.")
//    })
//	public ResponseEntity<Void> changeLogin(@RequestBody ChangeLogin user) {
//		boolean newTrainee = facade.changeLogin(user);
//		if (newTrainee) {
//			return ResponseEntity.status(200).build();
//		} else {
//			return ResponseEntity.status(400).build();
//		}
//    }

}
