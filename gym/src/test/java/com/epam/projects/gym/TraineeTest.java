package com.epam.projects.gym;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.epam.projects.gym.application.dto.request.ChangeUserStatus;
import com.epam.projects.gym.application.dto.request.TraineeRegister;
import com.epam.projects.gym.application.dto.request.TraineeUpdate;
import com.epam.projects.gym.application.dto.response.TraineeProfile;
import com.epam.projects.gym.infrastructure.controller.TraineeController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class TraineeTest {

    private MockMvc mockMvc;
	
	@Autowired
	private TraineeController traineeController;
	
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(traineeController).build();
	}
	
	@Test
	@Transactional
	void testListTrainees() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/trainees"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@Transactional
	void testCreateTrainee() throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		TraineeRegister requestBody = new TraineeRegister();
		requestBody.setFirstName("Luis");
		requestBody.setLastName("Herrera");
		requestBody.setAddress("test 123");
		requestBody.setDateOfBirth(LocalDate.now());
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/trainees")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isCreated());
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/trainees")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	@Transactional
	void testCreateTraineeWithoutLastName() throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		TraineeRegister requestBody = new TraineeRegister();
		requestBody.setFirstName("Luis");
		requestBody.setAddress("test 123");
		requestBody.setDateOfBirth(LocalDate.now());
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/trainees")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	@Transactional
	void testUpdateTrainee() throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		TraineeRegister requestBody = new TraineeRegister();
		requestBody.setFirstName("Benancio");
		requestBody.setLastName("Martinez");
		requestBody.setAddress("test 123");
		requestBody.setDateOfBirth(LocalDate.now());
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/trainees")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isCreated());
		
		String username = requestBody.getFirstName() + "." +  requestBody.getLastName();
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/trainees/{username}", username))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
		
		TraineeProfile createdTrainee = objectMapper.readValue(result.getResponse().getContentAsString(), TraineeProfile.class);
		
		TraineeUpdate requestBody2 = new TraineeUpdate();
		
		requestBody2.setUsername(username);
		requestBody2.setFirstName(createdTrainee.getFirstName());
		requestBody2.setLastName(createdTrainee.getLastName());
		requestBody2.setDateOfBirth(createdTrainee.getDateOfBirth());
		requestBody2.setAddress("Update test");
		requestBody2.setActive(true);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/api/trainees")
				.content(objectMapper.writeValueAsString(requestBody2))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@Transactional
	void testFindTraineeByUsername() throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		TraineeRegister requestBody = new TraineeRegister();
		requestBody.setFirstName("Alexander");
		requestBody.setLastName("Mina");
		requestBody.setAddress("test 123");
		requestBody.setDateOfBirth(LocalDate.now());
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/trainees")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isCreated());
		
		String username = requestBody.getFirstName() + "." +  requestBody.getLastName();
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/trainees/{username}", username))
                .andExpect(MockMvcResultMatchers.status().isOk());
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/trainees/{username}", "pepitoperez"))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	@Transactional
	void testDeleteTrainee() throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		TraineeRegister requestBody = new TraineeRegister();
		requestBody.setFirstName("Facundo");
		requestBody.setLastName("Rosalez");
		requestBody.setAddress("test 123");
		requestBody.setDateOfBirth(LocalDate.now());
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/trainees")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isCreated());
		
		String username = requestBody.getFirstName() + "." +  requestBody.getLastName();
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/trainees/{username}", username))
                .andExpect(MockMvcResultMatchers.status().isOk());
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/trainees/{username}", username))
                .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@Transactional
	void testDeActivateTrainee() throws Exception {
		ChangeUserStatus request = new ChangeUserStatus();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		TraineeRegister requestBody = new TraineeRegister();
		requestBody.setFirstName("Facundo");
		requestBody.setLastName("Rosalez");
		requestBody.setAddress("test 123");
		requestBody.setDateOfBirth(LocalDate.now());
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/trainees")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isCreated());
		
		String username = requestBody.getFirstName() + "." +  requestBody.getLastName();
		
		request.setUsername(username);
		request.setActive(false);		
		
		mockMvc.perform(MockMvcRequestBuilders.patch("/api/trainees")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@Transactional
	void testDeActivateTraineeWithWrongName() throws Exception {
		ChangeUserStatus request = new ChangeUserStatus();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		TraineeRegister requestBody = new TraineeRegister();
		requestBody.setFirstName("Facundo");
		requestBody.setLastName("Rosalez");
		requestBody.setAddress("test 123");
		requestBody.setDateOfBirth(LocalDate.now());
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/trainees")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isCreated());
		
		String username = requestBody.getFirstName() + "." +  requestBody.getLastName();
		
		request.setUsername(username + "2");
		request.setActive(false);
		
		mockMvc.perform(MockMvcRequestBuilders.patch("/api/trainees")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

}
