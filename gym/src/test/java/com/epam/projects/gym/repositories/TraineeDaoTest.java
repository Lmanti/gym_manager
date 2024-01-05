package com.epam.projects.gym.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.projects.gym.models.Trainee;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TraineeDaoTest {
	
	@Autowired
	private TraineeDao dao;

	@Test
	@Order(1)
	void testCreateTrainee() {
		Trainee trainee = new Trainee(UUID.randomUUID());
		
		dao.save(trainee);
		
		assertEquals(1, dao.findAll().size(), "We should be able to create a new trainee using the DAO.");
		assertEquals(trainee.getId(), dao.findAll().get(0).getId(), "Trainee info should be saved and readed correctly.");
		
	}
	
	@Test
	@Order(2)
	void testGetTraineeById() {
		Trainee trainee = new Trainee(UUID.randomUUID());
		
		dao.save(trainee);
		
		assertEquals(trainee.getId(), dao.findById(trainee.getId()).getId(), "We sould be able to search a trainee by ID using DAO.");
	}
	
	@Test
	@Order(3)
	void testUpdateTrainee() {
		Trainee trainee = new Trainee(UUID.randomUUID());
		
		dao.save(trainee);
		
		trainee.setAddress("Example of adress.");
		trainee.getTrainers().add(UUID.randomUUID());
		
		dao.save(trainee);
		
		assertEquals(trainee.getId(), dao.findById(trainee.getId()).getId(), "We should be able to update a learner without losing integrity using DAO.");
		assertEquals(1, dao.findById(trainee.getId()).getTrainers().size(), "We should be able to update the trainings.");
		assertEquals("Example of adress.", dao.findById(trainee.getId()).getAddress(), "We should be able to update personal data.");
		assertEquals(3, dao.findAll().size(), "We shouldn't duplicate data.");
	}
	
	@Test
	@Order(4)
	void testDeleteTrainee() {
		Trainee trainee = new Trainee(UUID.randomUUID());
		Trainee trainee2 = new Trainee(UUID.randomUUID());
		dao.save(trainee);
		dao.save(trainee2);
		
		UUID traineeId = trainee.getId();
		
		dao.deleteById(trainee.getId());
		
		assertEquals(4, dao.findAll().size(), "We should be able to delete a trainee.");
		assertEquals(null, dao.findById(traineeId), "We should be able to delete the correct trainee.");
	}

}
