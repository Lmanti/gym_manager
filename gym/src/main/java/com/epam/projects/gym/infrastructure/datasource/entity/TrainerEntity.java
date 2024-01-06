package com.epam.projects.gym.infrastructure.datasource.entity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import com.epam.projects.gym.domain.entity.Trainer;
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
public class TrainerEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	@JsonProperty("trainerId")
	private UUID trainerId;
	
	@OneToOne
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

	public Trainer toDomain() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Trainer getInfo() {
		return new Trainer(
				trainerId,
				specialization.getInfo(),
				userId.getInfo(),
				null);
	}
	
}
