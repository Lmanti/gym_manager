package com.epam.projects.gym.infrastructure.datasource.in_memory.config.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.epam.projects.gym.infrastructure.datasource.in_memory.config.StorageDao;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StorageImpl implements StorageDao {
	
	private final String ENTITY_NAME = "database";
	
	private Map<String, Object> jsonDataMap;
	
	@Value("${data.file.path}")
	private String dataFilePath;
	
	public StorageImpl(Map<String, Object> database) {
		this.jsonDataMap = database;
	}
	
	public StorageImpl() {
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void initialize() {
		try {
			InputStream inputStream = new ClassPathResource(dataFilePath).getInputStream();
			if (inputStream != null && inputStream.available() > 0) {
                ObjectMapper objectMapper = new ObjectMapper();
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
