package com.epam.projects.gym.infrastructure.datasource.entity;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.epam.projects.gym.domain.entity.Trainer;
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
public class TrainerEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	@JsonProperty("trainerId")
	private UUID trainerId;
	
	@ManyToOne
	@JoinColumn(name = "trainingTypeId")
	@JsonProperty("specialization")
    private TrainingTypeEntity specialization;

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
	@JsonProperty("userId")
	private UserEntity userId;

	@ManyToMany
    @JoinTable(
            name = "trainer_trainee",
            joinColumns = @JoinColumn(name = "trainerId"),
            inverseJoinColumns = @JoinColumn(name = "traineeId")
    )
	@JsonProperty("trainees")
	private List<TraineeEntity> trainees;
	
	@OneToMany(mappedBy = "trainerId")
	@JsonProperty("trainingId")
	private List<TrainingEntity> trainingId;
	
	public TrainerEntity(
			@NonNull TrainingTypeEntity specialization,
			@NonNull UserEntity userId
			) {
		this.specialization = specialization;
		this.userId = userId;
	}

	public Trainer toDomain() {
		Trainer trainer = new Trainer(
				userId.getFirstName(),
				userId.getLastName(),
				userId.getUsername(),
				userId.getPassword(),
				userId.getIsActive(),
				specialization.toDomain());
		trainer.setUserId(userId.getUserId());
		trainer.setId(trainerId);
		trainer.setTrainees(trainees != null && !trainees.isEmpty()
				? trainees.stream().map(TraineeEntity::getBasicDomain).collect(Collectors.toList())
				: Collections.emptyList());
		return trainer;
	}
	
	public Trainer getBasicDomain() {
		Trainer trainer = new Trainer(
				userId.getFirstName(),
				userId.getLastName(),
				userId.getUsername(),
				userId.getPassword(),
				userId.getIsActive(),
				specialization.toDomain());
		trainer.setUserId(userId.getUserId());
		trainer.setId(trainerId);
		return trainer;
	}
	
}
