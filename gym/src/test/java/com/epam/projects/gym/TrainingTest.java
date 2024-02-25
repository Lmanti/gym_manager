package com.epam.projects.gym;

import java.time.LocalDate;

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

import com.epam.projects.gym.application.dto.request.TrainingCreate;
import com.epam.projects.gym.domain.enums.Specialization;
import com.epam.projects.gym.infrastructure.controller.TrainingController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@Transactional
@Rollback
@TestPropertySource(locations = "classpath:application-test.properties")
class TrainingTest {

	private MockMvc mockMvc;
	
	@Autowired
	private TrainingController trainingController;
	
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(trainingController).build();
	}
	
	@Test
	void testCreateTraining() throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		TrainingCreate requestBody = new TrainingCreate();
		
		requestBody.setTraineeUsername("Luis.Herrera8723");
		requestBody.setTrainerUsername("Melissa.Lopez");
		requestBody.setTrainingName("Fitness prueba");
		requestBody.setTrainingDate(LocalDate.now());
		requestBody.setDuration(10);
		requestBody.setTrainingTypeName(Specialization.Fitness);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/training")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	void testGetTraineeTrainingList() throws Exception {
		
		testCreateTraining();
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/training/trainees")
				.param("username", "Luis.Herrera8723")
				.param("trainerName", "Melissa.Lopez")
				.param("periodFrom", LocalDate.of(2024, 2, 1).toString())
				.param("periodTo", LocalDate.of(2024, 2, 29).toString())
				.param("trainingType", "Fitness"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void testGetTrainerTrainingList() throws Exception {
		
		testCreateTraining();
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/training/trainers")
				.param("username", "Melissa.Lopez")
				.param("traineeName", "Luis.Herrera8723")
				.param("periodFrom", LocalDate.of(2024, 2, 1).toString())
				.param("periodTo", LocalDate.of(2024, 2, 29).toString()))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
