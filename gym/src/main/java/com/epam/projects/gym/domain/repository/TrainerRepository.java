package com.epam.projects.gym.domain.repository;

import java.util.List;

import com.epam.projects.gym.domain.entity.Trainer;

public interface TrainerRepository {

	public List<Trainer> getAllTrainers();

	public Trainer findByUsername(String username);

	public Trainer createTrainer(Trainer newTrainer);

}
