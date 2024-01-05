package com.epam.projects.gym.mappers;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.epam.projects.gym.dto.UserDto;
import com.epam.projects.gym.models.User;

@Component
public class UserMapperImpl implements UserMapper {
	
	/**
	 * Logger for UserMapperImpl.
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserMapperImpl.class);

	@Override
	public User mapDtoToUser(UserDto dto) {
		if (validateUser(dto)) {
			User user = new User(
					dto.getFirstName(),
					dto.getLastName(),
					true); // is active by default.
			if (dto.getId() != null) { // set the incoming props if this function is used for update.
				user.setId(dto.getId());
				user.setActive(dto.isActive());
				user.setUsername(dto.getUsername());
				user.setPassword(dto.getPassword());
			} else {
				user.setUsername(createUsername(dto.getFirstName(), dto.getLastName()));
				user.setPassword(createPasword(dto.getFirstName(), dto.getLastName()));
			}
			return user;
		} else {
			logger.error("User is not valid, please check the info.");
			return null;
		}
	}
	
	private boolean validateUser(UserDto dto) {
		boolean valid = true;
		if (dto.getFirstName() == null || dto.getFirstName().isEmpty()) {
			logger.warn("User first name cannot be empty.");
			valid = false;
		}
		if (dto.getLastName() == null || dto.getLastName().isEmpty()) {
			logger.warn("User last name cannot be empty.");
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
