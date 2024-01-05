package com.epam.projects.gym.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.projects.gym.dto.basics.TraineeBasicDto;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TraineeServiceTest {
	
	
	private TraineeService service;
	
	@Autowired
	public TraineeServiceTest(TraineeService service) {
		this.service = service;
	}
	
//	@Test
//	@Order(1)
//	void testCreateTrainee() {
//		
//		LocalDate fecha = LocalDate.of(1990, 12, 11);
//
//		TraineeBasicDto trainee3 = new TraineeBasicDto(
//				"User3",
//				"Test3",
//				"usertest3",
//				"test1234",
//				Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant()),
//				"Example Address");
//		
//		TraineeBasicDto justCreated = service.createTrainee(trainee3);
//		
//		TraineeBasicDto justRequested = service.getTraineeById(justCreated.getTraineeId());
//		
//		assertEquals(justCreated.getId(), justRequested.getId(), "The creation ID must be the same as the query ID.");
//		assertEquals(justCreated.getFirstName(), justRequested.getFirstName(), "The creation first name must be the same as the query first name.");
//		assertEquals(justCreated.getLastName(), justRequested.getLastName(), "The creation last name must be the same as the query last name.");
//		assertEquals(justCreated.getUsername(), justRequested.getUsername(), "The creation username must be the same as the query username.");
//		assertEquals(justCreated.getPassword(), justRequested.getPassword(), "The creation password must be the same as the query password.");
//		assertEquals(justCreated.isActive(), justRequested.isActive(), "The creation isActive field must be the same as the query isActive field.");
//		assertEquals(justCreated.getTraineeId(), justRequested.getTraineeId(), "The creation trainee id must be the same as the query trainee id.");
//		assertEquals(justCreated.getDateOfBirth(), justRequested.getDateOfBirth(), "The creation date of birth must be the same as the query date of birth.");
//		assertEquals(justCreated.getAddress(), justRequested.getAddress(), "The creation address must be the same as the query address.");
//		assertEquals(justCreated.getTrainings(), justRequested.getTrainings(), "The creation training list must be the same as the query training list.");
//	}

	@Test
	@Order(2)
	void testGetAllTrainees() {
		assertEquals(1, service.getAllTrainees().size(), "We should be able to get all trainees from the database.");
	}
	
//	@Test
//	@Order(3)
//	void testGetTraineeById() {
//		LocalDate fecha = LocalDate.of(1990, 12, 11);
//		
//		TraineeBasicDto trainee3 = new TraineeBasicDto(
//				"User3",
//				"Test3",
//				"usertest3",
//				"test1234",
//				Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant()),
//				"Example Address");
//
//		TraineeBasicDto justCreated = service.createTrainee(trainee3);
//
//		TraineeBasicDto justRequested = service.getTraineeById(justCreated.getTraineeId());
//		
//		assertEquals(justCreated.getId(), justRequested.getId(), "The creation ID must be the same as the query ID.");
//		assertEquals(justCreated.getFirstName(), justRequested.getFirstName(), "The creation first name must be the same as the query first name.");
//		assertEquals(justCreated.getLastName(), justRequested.getLastName(), "The creation last name must be the same as the query last name.");
//		assertEquals(justCreated.getUsername(), justRequested.getUsername(), "The creation username must be the same as the query username.");
//		assertEquals(justCreated.getPassword(), justRequested.getPassword(), "The creation password must be the same as the query password.");
//		assertEquals(justCreated.isActive(), justRequested.isActive(), "The creation isActive field must be the same as the query isActive field.");
//		assertEquals(justCreated.getTraineeId(), justRequested.getTraineeId(), "The creation trainee id must be the same as the query trainee id.");
//		assertEquals(justCreated.getDateOfBirth(), justRequested.getDateOfBirth(), "The creation date of birth must be the same as the query date of birth.");
//		assertEquals(justCreated.getAddress(), justRequested.getAddress(), "The creation address must be the same as the query address.");
//		assertEquals(justCreated.getTrainings(), justRequested.getTrainings(), "The creation training list must be the same as the query training list.");
//	}
	
//	@Test
//	@Order(4)
//	void testUpdateTrainee() {
//		LocalDate fecha = LocalDate.of(1990, 12, 11);
//
//		TraineeBasicDto trainee3 = new TraineeBasicDto(
//				"User3",
//				"Test3",
//				"usertest3",
//				"test1234",
//				Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant()),
//				"Example Address");
//		
//		TraineeBasicDto justCreated = service.createTrainee(trainee3);
//		boolean active = justCreated.isActive();
//		
//		justCreated.setActive(false);
//		justCreated.setFirstName("UserModificado");
//		justCreated.getTrainings().add(UUID.randomUUID());
//		
//		TraineeBasicDto justModified = service.updateTrainee(justCreated);
//		
//		assertEquals(justCreated.getId(), justModified.getId(), "The creation ID must be the same as the query ID.");
//		assertNotEquals(trainee3.getFirstName(), justModified.getFirstName(), "The creation first name must be the same as the query first name.");
//		assertEquals(justCreated.getLastName(), justModified.getLastName(), "The creation last name must be the same as the query last name.");
//		assertEquals(justCreated.getUsername(), justModified.getUsername(), "The creation username must be the same as the query username.");
//		assertEquals(justCreated.getPassword(), justModified.getPassword(), "The creation password must be the same as the query password.");
//		assertNotEquals(active, justModified.isActive(), "The creation isActive field must be the same as the query isActive field.");
//		assertEquals(justCreated.getTraineeId(), justModified.getTraineeId(), "The creation trainee id must be the same as the query trainee id.");
//		assertEquals(justCreated.getDateOfBirth(), justModified.getDateOfBirth(), "The creation date of birth must be the same as the query date of birth.");
//		assertEquals(justCreated.getAddress(), justModified.getAddress(), "The creation address must be the same as the query address.");
//		assertNotEquals(Collections.emptyList(), justModified.getTrainings(), "The creation training list must be the same as the query training list.");
//	}
	
//	@Test
//	@Order(5)
//	void testDeleteTrainee() {
//		
//		LocalDate fecha = LocalDate.of(1990, 12, 11);
//
//		TraineeBasicDto trainee3 = new TraineeBasicDto(
//				"User3",
//				"Test3",
//				"usertest3",
//				"test1234",
//				Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant()),
//				"Example Address");
//		
//		TraineeBasicDto justCreated = service.createTrainee(trainee3);
//		
//		UUID traineeId = justCreated.getTraineeId();
//		
//		service.deleteTrainee(justCreated.getTraineeId());
//		
//		assertEquals(null, service.getTraineeById(traineeId), "We should be able to delete a Trainee.");
//		
//	}

}
