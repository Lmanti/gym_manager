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

import com.epam.projects.gym.application.dto.request.ChangeUserStatus;
import com.epam.projects.gym.application.dto.request.TrainerRegister;
import com.epam.projects.gym.application.dto.request.TrainerUpdate;
import com.epam.projects.gym.domain.enums.Specialization;
import com.epam.projects.gym.infrastructure.controller.TrainerController;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@Transactional
@Rollback
@TestPropertySource(locations = "classpath:application-test.properties")
class TrainerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private TrainerController trainerController;
	
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(trainerController).build();
	}

	@Test
	void testListTrainees() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/trainers"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void testCreateTrainer() throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		TrainerRegister requestBody = new TrainerRegister();
		requestBody.setFirstName("Luis");
		requestBody.setLastName("Herrera");
		requestBody.setSpecialization(Specialization.Resistance);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/trainers")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	void testCreateTrainerWithExistingUsername() throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		TrainerRegister requestBody = new TrainerRegister();
		requestBody.setFirstName("Melissa");
		requestBody.setLastName("Lopez");
		requestBody.setSpecialization(Specialization.Resistance);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/trainers")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	void testUpdateTrainer() throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		TrainerUpdate requestBody = new TrainerUpdate();
		
		String username = "Melissa.Lopez";
		
		requestBody.setUsername(username);
		requestBody.setFirstName("Meli");
		requestBody.setLastName("Diaz");
		requestBody.setSpecialization(Specialization.Resistance);
		requestBody.setActive(true);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/api/trainers")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	@Test
	void testFindTrainerByUsername() throws Exception {
		
		String username = "Melissa.Lopez";
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/trainers/{username}", username))
                .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void testDeActivateTrainee() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		ChangeUserStatus request = new ChangeUserStatus();

		
		String username = "Melissa.Lopez";
		
		request.setUsername(username);
		request.setActive(false);		
		
		mockMvc.perform(MockMvcRequestBuilders.patch("/api/trainers")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void testFindNonAssociatedTrainersByTraineeUsername() throws Exception {
		
		String username = "Luis.Herrera8723";
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/trainers/notAssociated").param("username", username))
                .andExpect(MockMvcResultMatchers.status().isOk());
	}

}
