package com.test.doafb.codingassignment.dao;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * Base class for DAOs
 *
 * @author dinesh.singla
 *
 */
public abstract class BaseJsonDAO {

	private final ResourceLoader resourceLoader;

	public BaseJsonDAO(ResourceLoader resourceLoader) {
		super();
		this.resourceLoader = resourceLoader;
	}

	/**
	 * Method to load the resource if present
	 *
	 * @param fileName of the resource that needs to be loaded
	 * @return the resource
	 */
	protected Resource loadResourceFromClasspath(String fileName) {
        return resourceLoader.getResource("classpath:" + Constants.DATA_DIR +
				Constants.PATH_SEPARATOR + fileName + Constants.JSON_EXTENSION);
    }
}
