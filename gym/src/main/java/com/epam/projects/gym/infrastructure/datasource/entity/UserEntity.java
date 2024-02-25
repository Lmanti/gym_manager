package com.epam.projects.gym.infrastructure.datasource.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.epam.projects.gym.domain.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class UserEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	@JsonProperty("userid")
	private String userId;
	
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
	
	public UserEntity(
			@NonNull String firstName,
			@NonNull String lastName,
			@NonNull String username,
			@NonNull String password,
			@NonNull Boolean isActive
			) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.isActive = isActive;
	}

	public User toDomain() {
		User user = new User(firstName, lastName, username, password, isActive);
		user.setUserId(userId);
		user.setTraineeId(traineeId != null ? traineeId.toDomain() : null);
		user.setTrainerId(trainerId != null ? trainerId.toDomain() : null);		
		return user;
	}

}
