package com.epam.projects.gym.infrastructure.datasource.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.epam.projects.gym.domain.entity.Trainee;
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
public class TraineeEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	@JsonProperty("traineeId")
	private String traineeId;
	
	@Column(nullable = true)
	@JsonProperty("dateOfBirth")
	private LocalDate dateOfBirth;
	
	@Column(nullable = true)
	@JsonProperty("address")
	private String address;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
	@JsonProperty("userId")
	private UserEntity userId;
	
	@ManyToMany
    @JoinTable(
            name = "trainer_trainee",
            joinColumns = @JoinColumn(name = "traineeId"),
            inverseJoinColumns = @JoinColumn(name = "trainerId")
    )
	@JsonProperty("trainers")
	private List<TrainerEntity> trainers;
	
	@OneToMany(mappedBy = "traineeId")
	@JsonProperty("trainingId")
	private List<TrainingEntity> trainingId;
	
	public TraineeEntity(
			LocalDate dateOfBirth,
			String address,
			@NonNull UserEntity userId
			) {
		this.dateOfBirth = dateOfBirth;
		this.address = address;
		this.userId = userId;
	}

	public Trainee toDomain() {
		Trainee trainee = new Trainee(
				userId.getFirstName(),
				userId.getLastName(),
				userId.getUsername(),
				userId.getPassword(),
				userId.getIsActive(),
				dateOfBirth,
				address);
		trainee.setUserId(userId.getUserId());
		trainee.setId(traineeId);
		trainee.setTrainers(trainers != null && !trainers.isEmpty()
				? trainers.stream().map(TrainerEntity::getBasicDomain).collect(Collectors.toList())
				: Collections.emptyList());
		return trainee;
	}
	
	public Trainee getBasicDomain() {
		Trainee trainee = new Trainee(
				userId.getFirstName(),
				userId.getLastName(),
				userId.getUsername(),
				userId.getPassword(),
				userId.getIsActive(),
				dateOfBirth,
				address);
		trainee.setUserId(userId.getUserId());
		trainee.setId(traineeId);
		return trainee;
	}
	
}
