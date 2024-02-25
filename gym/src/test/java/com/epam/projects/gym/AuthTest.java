package com.epam.projects.gym;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.epam.projects.gym.application.dto.request.ChangeLogin;
import com.epam.projects.gym.infrastructure.controller.AuthController;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@Transactional
@Rollback
@TestPropertySource(locations = "classpath:application-test.properties")
class AuthTest {
	
private MockMvc mockMvc;
	
	@Autowired
	private AuthController authController;
	
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
	}

	@Test
	void testAuthTrainee() throws Exception {
		
		String username = "Luis.Herrera8723";
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/auth")
				.param("username", username)
				.param("password", "familia2"))
        .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void testChangeTraineePassword() throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		String username = "Luis.Herrera8723";
		
		ChangeLogin request = new ChangeLogin();
		
		request.setUsername(username);
		request.setPassword("familia2");
		request.setNewPassword("prueba123");
		
		mockMvc.perform(MockMvcRequestBuilders.put("/api/auth/trainee")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void testChangeTraineePasswordWithWrongPassword() throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		String username = "Luis.Herrera8723";
		
		ChangeLogin request = new ChangeLogin();
		
		request.setUsername(username);
		request.setPassword("familia212121");
		request.setNewPassword("prueba123");
		
		mockMvc.perform(MockMvcRequestBuilders.put("/api/auth/trainee")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}
	
	@Test
	void testChangeTrainerPassword() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		
		String username = "Melissa.Lopez";
		
		ChangeLogin request = new ChangeLogin();
		
		request.setUsername(username);
		request.setPassword("ee3lLM6lls");
		request.setNewPassword("prueba123");
		
		mockMvc.perform(MockMvcRequestBuilders.put("/api/auth/trainer")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void testChangeTrainerPasswordWithWrongPassword() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		
		String username = "Melissa.Lopez";
		
		ChangeLogin request = new ChangeLogin();
		
		request.setUsername(username);
		request.setPassword("ee3lLM6llsadasdqwe12");
		request.setNewPassword("prueba123");
		
		mockMvc.perform(MockMvcRequestBuilders.put("/api/auth/trainer")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

}
