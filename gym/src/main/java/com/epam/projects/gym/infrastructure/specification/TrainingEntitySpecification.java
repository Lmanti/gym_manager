package com.epam.projects.gym.infrastructure.specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.epam.projects.gym.application.specification.TrainingSpecification;
import com.epam.projects.gym.infrastructure.datasource.entity.TraineeEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.TrainerEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.TrainingEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.TrainingTypeEntity;
import com.epam.projects.gym.infrastructure.datasource.entity.UserEntity;

public class TrainingEntitySpecification implements Specification<TrainingEntity> {

	private static final long serialVersionUID = 1L;

	private String traineeUsername;
	private String trainerUsername;
	private LocalDate periodFrom;
	private LocalDate periodTo;
	private String trainerName;
	private String traineeName;
	private String trainingTypeName;
	
	public TrainingEntitySpecification(TrainingSpecification specification) {
		this.traineeUsername = specification.getTraineeUsername();
		this.trainerUsername = specification.getTrainerUsername();
		this.periodFrom = specification.getPeriodFrom();
		this.periodTo = specification.getPeriodTo();
		this.trainerName = specification.getTrainerName();
		this.traineeName = specification.getTraineeName();
		this.trainingTypeName = specification.getTrainingTypeName();
	}

	@Override
	public Predicate toPredicate(Root<TrainingEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		
		Join<TrainingEntity, TraineeEntity> traineeJoin = root.join("traineeId", JoinType.INNER);
		Join<TraineeEntity, UserEntity> traineeUserJoin = traineeJoin.join("userId", JoinType.INNER);
		Join<TrainingEntity, TrainerEntity> trainerJoin = root.join("trainerId", JoinType.INNER);
		Join<TrainerEntity, TrainerEntity> trainerUserJoin = trainerJoin.join("userId", JoinType.INNER);
		Join<TrainingEntity, TrainingTypeEntity> trainingTypeJoin = root.join("trainingTypeId", JoinType.INNER);
		
		if (traineeUsername != null && !traineeUsername.isEmpty()) {
			predicates.add(criteriaBuilder.equal(traineeUserJoin.get("username"), traineeUsername));
		}
		if (trainerUsername != null && !trainerUsername.isEmpty()) {
			predicates.add(criteriaBuilder.equal(trainerUserJoin.get("username"), trainerUsername));
		}
		if (trainingTypeName != null && !trainingTypeName.isEmpty()) {
			predicates.add(criteriaBuilder.equal(trainingTypeJoin.get("name"), trainingTypeName));
		}
		if (traineeName != null && !traineeName.isEmpty()) {
			predicates.add(criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.upper(traineeUserJoin.get("firstName")), "%" + traineeName.toUpperCase() + "%"),
					criteriaBuilder.like(criteriaBuilder.upper(traineeUserJoin.get("lastName")), "%" + traineeName.toUpperCase() + "%")));
		}
		if (trainerName != null && !trainerName.isEmpty()) {
			predicates.add(criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.upper(trainerUserJoin.get("firstName")), "%" + trainerName.toUpperCase() + "%"),
					criteriaBuilder.like(criteriaBuilder.upper(trainerUserJoin.get("lastName")), "%" + trainerName.toUpperCase() + "%")));
		}
		if (periodFrom != null && periodTo != null) {
			predicates.add(criteriaBuilder.between(root.get("trainingDate"), criteriaBuilder.literal(periodFrom), criteriaBuilder.literal(periodTo)));
		}
		
		return query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).distinct(true).getRestriction();
	}

}
