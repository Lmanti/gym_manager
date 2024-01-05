package com.epam.projects.gym.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.projects.gym.models.User;

@SpringBootTest
class UserDaoTest {
	
	@Autowired
	private UserDao dao;

	@Test
	void testUserDao() {
		User user = new User("User", "Test", true);
		
		dao.save(user);
		
		assertEquals(user.getId(), dao.findById(user.getId()).getId(), "We sould be able to create an user using DAO.");
		assertEquals(user.getId(), dao.findById(user.getId()).getId(), "We sould be able to search an user by ID using DAO.");
	}
	
	@Test
	void testDeleteUser() {
		User user = new User("User", "Test", true);
		
		dao.save(user);
		
		UUID id = user.getId();
		
		dao.deleteById(user.getId());
		
		assertEquals(null, dao.findById(id), "We sould be able to delete an user using DAO.");
	}

}
