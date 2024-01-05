package com.epam.projects.gym.mappers;

import com.epam.projects.gym.dto.UserDto;
import com.epam.projects.gym.models.User;

public interface UserMapper {
	
	public User mapDtoToUser(UserDto dto);
	
	public String getSerialNumber();
	
}
