package com.epam.projects.gym.repositories;

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

import com.epam.projects.gym.models.Training;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TrainingDaoTest {

	@Autowired
	private TrainingDao dao;

	@Test
	@Order(1)
	void testCreateTraining() {
		LocalDate fecha = LocalDate.of(1990, 12, 11);
		Training training = new Training(
				UUID.randomUUID(),
				UUID.randomUUID(),
				"Test",
				UUID.randomUUID(),
				Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant()),
				10);
		
		dao.save(training);
		
		assertEquals(1, dao.findAll().size(), "We should be able to create a new Training using the DAO.");
		assertEquals(training.getId(), dao.findAll().get(0).getId(), "Training info should be saved and readed correctly.");
	}
	
	@Test
	@Order(2)
	void testGetTrainingById() {
		LocalDate fecha = LocalDate.of(1990, 12, 11);
		Training training = new Training(
				UUID.randomUUID(),
				UUID.randomUUID(),
				"Test",
				UUID.randomUUID(),
				Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant()),
				10);
		
		dao.save(training);
		
		assertEquals(training.getId(), dao.findById(training.getId()).getId(), "We sould be able to search a training by ID using DAO.");
	}
	
	@Test
	@Order(3)
	void testUpdateTrainee() {
		LocalDate fecha = LocalDate.of(1990, 12, 11);
		Training training = new Training(
				UUID.randomUUID(),
				UUID.randomUUID(),
				"Test",
				UUID.randomUUID(),
				Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant()),
				10);
		
		dao.save(training);
		
		training.setName("Test2");
		training.setDuration(15);
		
		dao.save(training);
		
		assertEquals(training.getId(), dao.findById(training.getId()).getId(), "We should be able to update a training without losing integrity using DAO.");
		assertEquals(15, dao.findById(training.getId()).getDuration(), "We sould be able to change the duration.");
		assertEquals("Test2", dao.findById(training.getId()).getName(), "We should be able to update training data.");
		assertEquals(3, dao.findAll().size(), "We shouldn't duplicate data.");
	}
	
	@Test
	@Order(4)
	void testDeleteTrainee() {
		LocalDate fecha = LocalDate.of(1990, 12, 11);
		Training training1 = new Training(
				UUID.randomUUID(),
				UUID.randomUUID(),
				"Test1",
				UUID.randomUUID(),
				Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant()),
				10);
		
		Training training2 = new Training(
				UUID.randomUUID(),
				UUID.randomUUID(),
				"Test2",
				UUID.randomUUID(),
				Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant()),
				10);
		
		dao.save(training1);
		dao.save(training2);
		
		UUID trainingId = training1.getId();
		
		dao.deleteById(training1.getId());
		
		assertEquals(4, dao.findAll().size(), "We should be able to delete a training.");
		assertEquals(null, dao.findById(trainingId), "We should be able to delete the correct training.");
	}

}
