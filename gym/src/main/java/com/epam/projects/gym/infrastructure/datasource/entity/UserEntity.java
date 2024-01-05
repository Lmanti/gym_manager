package com.epam.projects.gym.infrastructure.datasource.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.epam.projects.gym.domain.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator = "uuid")
	@JsonProperty("id")
	private UUID id;
	
	@Column
	@JsonProperty("firstName")
	private String firstName;
	
	@Column
	@JsonProperty("lastName")
	private String lastName;
	
	@Column
	@JsonProperty("username")
	private String username;
	
	@Column
	@JsonProperty("password")
	private String password;
	
	@Column
	@JsonProperty("isActive")
	private Boolean isActive;
	
	@OneToOne(mappedBy = "userId")
	@JsonProperty("traineeId")
	private TraineeEntity traineeId;
	
	@OneToOne(mappedBy = "userId")
	@JsonProperty("TrainerId")
	private TrainerEntity trainerId;
	
	public User toDomain() {
		return new User(
				id,
				firstName,
				lastName,
				username,
				password,
				isActive,
				traineeId != null ? traineeId.toDomain() : null,
				trainerId != null ? trainerId.toDomain() : null);
	}

	public User toInfo() {
		return new User(
				id,
				firstName,
				lastName,
				username,
				password,
				isActive,
				null,
				null);
	}

}
