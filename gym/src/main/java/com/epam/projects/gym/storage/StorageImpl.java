package com.epam.projects.gym.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implementation for in-memory storage.
 * @author lherreram
 */
@Component
public class StorageImpl implements StorageDao {
	
	private final String ENTITY_NAME = "database";
	
	/**
	 * Logger for StorageImpl.
	 */
	private static final Logger logger = LoggerFactory.getLogger(StorageImpl.class);
	
	/**
	 * Database parsed to Java map.
	 */
	private Map<String, Object> jsonDataMap;
	
	/**
	 * File path to JSON file.
	 */
	@Value("${data.file.path}")
	private String dataFilePath;
	
	/**
	 * Constructor for testing.
	 * @param database
	 */
	public StorageImpl(Map<String, Object> database) {
		this.jsonDataMap = database;
	}
	
	/**
	 * Storage Constructor.
	 */
	public StorageImpl() {
	}

	/**
	 * Initialize the storage.
	 */
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void initialize() {
		try {
			Path filePath = Paths.get(dataFilePath);
			if (Files.exists(filePath) && Files.isReadable(filePath) && Files.size(filePath) > 0) {
                byte[] jsonData = Files.readAllBytes(filePath);

                ObjectMapper objectMapper = new ObjectMapper();
                jsonDataMap = (Map<String, Object>) objectMapper.readValue(jsonData, new TypeReference<Map<String, Object>>() {}).get(ENTITY_NAME);

                logger.info("Storage initialized.");
            } else {
                logger.warn("JSON file is empty or doesn't exist.");
            }		
		} catch (IOException e) {
			logger.error("Error initializing storage", e);
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
//			if (dataFilePath != null) {
//				ObjectMapper objectMapper = new ObjectMapper();
//				Path filePath = Paths.get(dataFilePath);
//				
//	            byte[] jsonData = objectMapper.writeValueAsBytes(newData);
//	            Files.write(filePath, jsonData, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
//	            logger.info("Data saved.");
//			}
			this.jsonDataMap = newData;
        } catch (Exception e) {
            logger.error("Error trying to save the data in the in-memory storage.", e);
            throw new RuntimeException("Error trying to read the n-memory storage.", e);
        }
	}
}
