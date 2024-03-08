package com.epam.projects.gym;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.epam.projects.gym.infrastructure.controller.TrainingTypeController;

@SpringBootTest
@Transactional
@Rollback
@TestPropertySource(locations = "classpath:application-test.properties")
class TrainingTypeTest {

	private MockMvc mockMvc;
	
	@Autowired
	private TrainingTypeController trainingTypeController;
	
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(trainingTypeController).build();
	}
	
	@Test
	void testListTrainingTypes() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/trainingTypes"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
