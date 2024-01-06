package com.epam.projects.gym.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Trainer Registration Details")
public class TrainerRegister extends UserRegister {
	
	public enum Specialization {
        Fitness("Fitness"),
        Yoga("Yoga"),
        Zumba("Zumba"),
        Stretching("Stretching"),
        Resistance("Resistance");

        private String label;

        Specialization(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
	
	/**
	 * Trainer's Specialization.
	 */
	@ApiModelProperty(
            value = "Trainer's Specialization",
            allowableValues = "Fitness, Yoga, Zumba, Stretching, Resistance"
    )
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Specialization specialization;

	/**
	 * @return the specialization
	 */
	public Specialization getSpecialization() {
		return specialization;
	}

	/**
	 * @param specialization the specialization to set
	 */
	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}

}
