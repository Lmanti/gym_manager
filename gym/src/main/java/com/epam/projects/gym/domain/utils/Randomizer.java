package com.epam.projects.gym.domain.utils;

import java.util.Random;

public class Randomizer {

	public static final String createUsername(String firstName, String lastname) {
		return firstName.concat(".").concat(lastname);
	}
	
	public static final String createPasword(String firstName, String lastname) {
        String input = firstName + lastname + getSerialNumber();
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();
        int max = 10;
        for (int i = 0; i < max; i++) {
            int randomIndex = random.nextInt(input.length());
            char randomChar = input.charAt(randomIndex);
            randomString.append(randomChar);
        }
        return randomString.toString();
    }
	
	public static final String getSerialNumber() {
		Random random = new Random();
		int randomNumber = random.nextInt(9000) + 1000;
		return String.valueOf(randomNumber);
	}
	
}
