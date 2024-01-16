package com.epam.projects.gym.domain.repository;

import java.util.List;
import java.util.Optional;

import com.epam.projects.gym.domain.entity.Trainer;

public interface TrainerRepository {

	public List<Trainer> getAllTrainers();

	public Optional<Trainer> findByUsername(String username);

	public Trainer createTrainer(Trainer newTrainer);

	public Trainer updateTrainer(Trainer trainer);

}
