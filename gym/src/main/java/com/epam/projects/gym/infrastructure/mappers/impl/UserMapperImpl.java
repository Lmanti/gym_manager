package com.epam.projects.gym.infrastructure.mappers.impl;

import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.epam.projects.gym.application.dto.UserDto;
import com.epam.projects.gym.infrastructure.datasource.entity.UserEntity;
import com.epam.projects.gym.infrastructure.mappers.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserMapperImpl implements UserMapper {

	@Override
	public UserEntity mapDtoToUser(UserDto dto) {
		if (validateUser(dto)) {
			UserEntity user = new UserEntity(
					UUID.randomUUID(),
					dto.getFirstName(),
					dto.getLastName(),
					null,
					null,
					true,
					null,
					null);
			if (dto.getId() != null) {
				user.setId(dto.getId());
				user.setIsActive(dto.isActive());
				user.setUsername(dto.getUsername());
				user.setPassword(dto.getPassword());
			} else {
				user.setUsername(createUsername(dto.getFirstName(), dto.getLastName()));
				user.setPassword(createPasword(dto.getFirstName(), dto.getLastName()));
			}
			return user;
		} else {
			log.error("User is not valid, please check the info.");
			return null;
		}
	}
	
	private boolean validateUser(UserDto dto) {
		boolean valid = true;
		if (dto.getFirstName() == null || dto.getFirstName().isEmpty()) {
			log.error("User first name cannot be empty.");
			valid = false;
		}
		if (dto.getLastName() == null || dto.getLastName().isEmpty()) {
			log.error("User last name cannot be empty.");
			valid = false;
		}
		return valid;
	}
	
	private String createUsername(String firstName, String lastname) {
		return firstName.concat(".").concat(lastname);
	}
	
	private String createPasword(String firstName, String lastname) {
        String input = firstName + lastname + getSerialNumber();
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();
        int max = 10;
        for (int i = 0; i < max; i++) {
            int randomIndex = random.nextInt(input.length());
            char randomChar = input.charAt(randomIndex);
            randomString.append(randomChar);
        }
        return randomString.toString();
    }
	
	public String getSerialNumber() {
		Random random = new Random();
		int randomNumber = random.nextInt(9000) + 1000;
		return String.valueOf(randomNumber);
	}

}
