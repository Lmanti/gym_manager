package com.epam.projects.gym.infrastructure.mappers;

import com.epam.projects.gym.application.dto.UserDto;
import com.epam.projects.gym.infrastructure.datasource.entity.UserEntity;

public interface UserMapper {
	
	public UserEntity mapDtoToUser(UserDto dto);
	
	public String getSerialNumber();
	
}
