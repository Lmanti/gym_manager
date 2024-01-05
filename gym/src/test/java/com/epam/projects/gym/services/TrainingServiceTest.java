package com.epam.projects.gym.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.projects.gym.dto.basics.TrainingBasicDto;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TrainingServiceTest {

	private TrainingService service;
	
	@Autowired
	public TrainingServiceTest(TrainingService service) {
		this.service = service;
	}
	
//	@Test
//	@Order(1)
//	void testCreateTraining() {
//		LocalDate fecha = LocalDate.of(1990, 12, 11);
//		TrainingBasicDto training = new TrainingBasicDto(
//				UUID.randomUUID(),
//				UUID.randomUUID(),
//				"Test",
//				UUID.randomUUID(),
//				Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant()),
//				10);
//		
//		TrainingBasicDto justCreated = service.createTraining(training);
//		
//		assertEquals(1, service.getAllTraining().size(), "We should be able to create a new Training using the service.");
//		assertEquals(justCreated.getId(), service.getAllTraining().get(0).getId(), "Training info should be saved and readed correctly.");
//	}
	
//	@Test
//	@Order(2)
//	void testGetTrainings() {
//		LocalDate fecha = LocalDate.of(1990, 12, 11);
//		TrainingBasicDto training = new TrainingBasicDto(
//				UUID.randomUUID(),
//				UUID.randomUUID(),
//				"Test",
//				UUID.randomUUID(),
//				Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant()),
//				10);
//		
//		service.createTraining(training);
//		
//		assertEquals(2, service.getAllTraining().size(), "We should be able to retrieve all Trainings using the service.");
//	}

}
