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
    
    public static Specialization identify(String name) {
    	switch (name) {
		case "Fitness":
			return Fitness;
		case "Yoga":
			return Yoga;
		case "Zumba":
			return Zumba;
		case "Stretching":
			return Stretching;
		case "Resistance":
			return Resistance;
		default:
			return null;
		}
    }
    
}
