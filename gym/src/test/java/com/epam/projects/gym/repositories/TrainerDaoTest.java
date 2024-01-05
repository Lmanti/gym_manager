package com.epam.projects.gym.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.projects.gym.models.Trainer;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TrainerDaoTest {
	
	@Autowired
	private TrainerDao dao;

	@Test
	@Order(1)
	void testCreateTrainer() {
		Trainer trainer = new Trainer(UUID.randomUUID());
		
		dao.save(trainer);
		
		assertEquals(1, dao.findAll().size(), "We should be able to create a new trainer using the DAO.");
		assertEquals(trainer.getId(), dao.findAll().get(0).getId(), "Trainer info should be saved and readed correctly.");
	}
	
	@Test
	@Order(2)
	void testGetTraineeById() {
		Trainer trainer = new Trainer(UUID.randomUUID());
		
		dao.save(trainer);
		
		assertEquals(trainer.getId(), dao.findById(trainer.getId()).getId(), "We sould be able to search a trainer by ID using DAO.");
	}
	
//	@Test
//	@Order(3)
//	void testUpdateTrainee() {
//		Trainer trainer = new Trainer(UUID.randomUUID());
//		
//		dao.save(trainer);
//		
//		UUID spec = UUID.randomUUID();
//		
//		trainer.setSpecialization(spec);;
//		trainer.getTrainings().add(UUID.randomUUID());
//		
//		dao.save(trainer);
//		
//		assertEquals(trainer.getId(), dao.findById(trainer.getId()).getId(), "We should be able to update a Trainer without losing integrity using DAO.");
//		assertEquals(1, dao.findById(trainer.getId()).getTrainings().size(), "We should be able to update the trainings.");
//		assertEquals(spec, dao.findById(trainer.getId()).getSpecialization(), "We should be able to update personal data.");
//		assertEquals(3, dao.findAll().size(), "We shouldn't duplicate data.");
//		
//	}
	
	@Test
	@Order(4)
	void testDeleteTrainee() {
		Trainer trainer = new Trainer(UUID.randomUUID());
		Trainer trainer2 = new Trainer(UUID.randomUUID());
		dao.save(trainer);
		dao.save(trainer2);
		
		UUID trainerId = trainer.getId();
		
		dao.deleteById(trainer.getId());
		
		assertEquals(4, dao.findAll().size(), "We should be able to delete a trainer.");
		assertEquals(null, dao.findById(trainerId), "We should be able to delete the correct trainer.");
	}

}
