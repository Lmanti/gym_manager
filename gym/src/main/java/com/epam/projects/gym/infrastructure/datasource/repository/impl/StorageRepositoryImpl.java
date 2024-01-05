package com.epam.projects.gym.infrastructure.datasource.repository.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import com.epam.projects.gym.infrastructure.datasource.repository.StorageRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class StorageRepositoryImpl implements StorageRepository {
	
	private final String ENTITY_NAME = "database";
	
	private Map<String, Object> jsonDataMap;
	
	@Value("${data.file.path}")
	private String dataFilePath;
	
	public StorageRepositoryImpl(Map<String, Object> database) {
		this.jsonDataMap = database;
	}
	
	public StorageRepositoryImpl() {
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void initialize() {
		try {
			InputStream inputStream = new ClassPathResource(dataFilePath).getInputStream();
			if (inputStream != null && inputStream.available() > 0) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                jsonDataMap = (Map<String, Object>) objectMapper.readValue(inputStream, new TypeReference<Map<String, Object>>() {}).get(ENTITY_NAME);

                log.info("Storage initialized.");
            } else {
                log.warn("JSON file is empty or doesn't exist.");
            }		
		} catch (IOException e) {
			log.error("Error initializing storage", e);
			throw new RuntimeException("Error trying to read the JSON file", e);
		}
	}

	@Override
	public Map<String, Object> getData() {
		return jsonDataMap;
	}

	@Override
	public void save(Map<String, Object> newData) {
		try {
			this.jsonDataMap = newData;
        } catch (Exception e) {
            log.error("Error trying to save the data in the in-memory storage.", e);
            throw new RuntimeException("Error trying to read the n-memory storage.", e);
        }
	}
}
