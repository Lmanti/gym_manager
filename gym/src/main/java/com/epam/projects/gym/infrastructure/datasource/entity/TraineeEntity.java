package com.epam.projects.gym.infrastructure.datasource.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import com.epam.projects.gym.domain.entity.Trainee;
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
public class TraineeEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	@JsonProperty("traineeId")
	private UUID traineeId;
	
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

	public Trainee toDomain() {
		return new Trainee(
				traineeId,
				dateOfBirth,
				address,
				userId.getInfo(),
				trainers != null && !trainers.isEmpty()
					? trainers.stream().map(TrainerEntity::getInfo).collect(Collectors.toList())
					: Collections.emptyList());
	}
	
	public Trainee getInfo() {
		return new Trainee(
				traineeId,
				dateOfBirth,
				address,
				userId.getInfo(),
				null);
	}
	
}
