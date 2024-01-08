package com.epam.projects.gym.application.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Specialization {

	Fitness("Fitness"),
    Yoga("Yoga"),
    Zumba("Zumba"),
    Stretching("Stretching"),
    Resistance("Resistance");

    private String label;
    
}
