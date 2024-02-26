package com.epam.projects.gym;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import com.epam.projects.gym.application.dto.request.TraineeRegister;
import com.epam.projects.gym.application.dto.request.TraineeUpdate;
import com.epam.projects.gym.application.dto.request.UpdateTrainerList;
import com.epam.projects.gym.infrastructure.controller.TraineeController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@Transactional
@Rollback
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
	void testListTrainees() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/trainees"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
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
	}
	
	@Test
	void testCreateTraineeWithExistingUsername() throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		TraineeRegister requestBody = new TraineeRegister();
		requestBody.setFirstName("Luis");
		requestBody.setLastName("Herrera8723");
		requestBody.setAddress("test 123");
		requestBody.setDateOfBirth(LocalDate.now());
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/trainees")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
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
	void testUpdateTrainee() throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		String username = "Luis.Herrera8723";
		
		TraineeUpdate requestBody2 = new TraineeUpdate();
		
		requestBody2.setUsername(username);
		requestBody2.setFirstName("Fulanito");
		requestBody2.setLastName("Martinez");
		requestBody2.setDateOfBirth(LocalDate.now());
		requestBody2.setAddress("Update test");
		requestBody2.setActive(true);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/api/trainees")
				.content(objectMapper.writeValueAsString(requestBody2))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void testUpdateTraineeWithWrongUsername() throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		String username = "Luis.Herrera87231111";
		
		TraineeUpdate requestBody2 = new TraineeUpdate();
		
		requestBody2.setUsername(username);
		requestBody2.setFirstName("Fulanito");
		requestBody2.setLastName("Martinez");
		requestBody2.setDateOfBirth(LocalDate.now());
		requestBody2.setAddress("Update test");
		requestBody2.setActive(true);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/api/trainees")
				.content(objectMapper.writeValueAsString(requestBody2))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void testFindTraineeByUsername() throws Exception {
		
		String username = "Luis.Herrera8723";
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/trainees/{username}", username))
                .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void testFindTraineeByWrongUsername() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/trainees/{username}", "pepitoperez"))
	    .andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void testDeleteTrainee() throws Exception {
		
		String username = "Luis.Herrera8723";
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/trainees/{username}", username))
                .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void testDeleteTraineeWithWrongUsername() throws Exception {
		
		String username = "Luis.Herrera872311111";
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/trainees/{username}", username))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void testDeActivateTrainee() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		ChangeUserStatus request = new ChangeUserStatus();

		
		String username = "Luis.Herrera8723";
		
		request.setUsername(username);
		request.setActive(false);		
		
		mockMvc.perform(MockMvcRequestBuilders.patch("/api/trainees")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void testDeActivateTraineeWithWrongName() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		ChangeUserStatus request = new ChangeUserStatus();

		
		String username = "Luis.Herrera";
		
		request.setUsername(username);
		request.setActive(false);		
		
		mockMvc.perform(MockMvcRequestBuilders.patch("/api/trainees")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void testEditTraineeTrainersList() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		UpdateTrainerList request = new UpdateTrainerList();

		
		String username = "Luis.Herrera8723";
		List<String> trainers = new ArrayList<String>();
		trainers.add("Melissa.Lopez");
		
		request.setUsername(username);
		request.setTrainerList(trainers);		
		
		mockMvc.perform(MockMvcRequestBuilders.put("/api/trainees/trainerList")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void testBadEditTraineeTrainersList() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		UpdateTrainerList request = new UpdateTrainerList();

		
		String username = "Luis.Herrera8723";
		List<String> trainers = new ArrayList<String>();
		trainers.add("fELIPITOjULIO");
		
		request.setUsername(username);
		request.setTrainerList(trainers);		
		
		mockMvc.perform(MockMvcRequestBuilders.put("/api/trainees/trainerList")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void testBadEditTraineeTrainersList2() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		UpdateTrainerList request = new UpdateTrainerList();

		
		String username = "Luis.Herrera872312123";
		List<String> trainers = new ArrayList<String>();
		trainers.add("Melissa.Lopez");
		
		request.setUsername(username);
		request.setTrainerList(trainers);		
		
		mockMvc.perform(MockMvcRequestBuilders.put("/api/trainees/trainerList")
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

}
