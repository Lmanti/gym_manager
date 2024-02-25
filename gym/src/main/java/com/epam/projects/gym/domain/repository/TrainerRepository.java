package com.epam.projects.gym.domain.repository;

import java.util.List;
import java.util.Optional;

import com.epam.projects.gym.domain.entity.Trainer;

public interface TrainerRepository {

	public List<Trainer> getAllTrainers();

	public Optional<Trainer> findByUsername(String username);

	public Optional<Trainer> createTrainer(Trainer newTrainer);

	public Optional<Trainer> updateTrainer(Trainer trainer);

	public List<Trainer> getAllNonAssociatedTrainers(String username);

	public Optional<Trainer> findByUsernameAndPassword(String username, String password);

	public boolean existByUsername(String username);

}
