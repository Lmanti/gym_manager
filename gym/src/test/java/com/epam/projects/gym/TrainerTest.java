package com.epam.projects.gym;

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

import com.epam.projects.gym.application.dto.request.TrainerRegister;
import com.epam.projects.gym.application.dto.request.TrainerUpdate;
import com.epam.projects.gym.application.dto.response.TrainerProfile;
import com.epam.projects.gym.domain.enums.Specialization;
import com.epam.projects.gym.infrastructure.controller.TrainerController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
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
	@Transactional
	void testListTrainees() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/trainers"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@Transactional
	void testCreateTrainer() throws Exception {
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		
		TrainerRegister requestBody = new TrainerRegister();
		requestBody.setFirstName("Luis");
		requestBody.setLastName("Herrera");
		requestBody.setSpecialization(Specialization.Resistance);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/trainers")
				.content(objectMapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
//	@Test
//	@Transactional
//	void testUpdateTrainer() throws Exception {
//		
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.registerModule(new JavaTimeModule());
//		
//		TrainerRegister requestBody = new TrainerRegister();
//		requestBody.setFirstName("Luis");
//		requestBody.setLastName("Herrera");
//		requestBody.setSpecialization(Specialization.Resistance);
//		
//		mockMvc.perform(MockMvcRequestBuilders.post("/api/trainers")
//				.content(objectMapper.writeValueAsString(requestBody))
//				.contentType(MediaType.APPLICATION_JSON))
//			.andExpect(MockMvcResultMatchers.status().isCreated()).andDo(x -> {
//				String username = requestBody.getFirstName() + "." +  requestBody.getLastName();
//				
//				MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/trainers/{username}", username))
//		                .andExpect(MockMvcResultMatchers.status().isOk())
//		                .andReturn();
//				
//				TrainerProfile createdTrainer = objectMapper.readValue(result.getResponse().getContentAsString(), TrainerProfile.class);
//				
//				TrainerUpdate requestBody2 = new TrainerUpdate();
//				
//				requestBody2.setUsername(username);
//				requestBody2.setFirstName(createdTrainer.getFirstName());
//				requestBody2.setLastName(createdTrainer.getLastName());
//				requestBody2.setSpecialization(Specialization.identify(createdTrainer.getSpecialization()));
//				requestBody2.setActive(true);
//				
//				mockMvc.perform(MockMvcRequestBuilders.put("/api/trainers")
//						.content(objectMapper.writeValueAsString(requestBody2))
//						.contentType(MediaType.APPLICATION_JSON))
//					.andExpect(MockMvcResultMatchers.status().isOk());
//				
//			});
//		
//	}

}
