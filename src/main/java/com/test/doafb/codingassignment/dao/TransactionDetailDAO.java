package com.test.doafb.codingassignment.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.doafb.codingassignment.dao.dto.TransactionDetail;
import com.test.doafb.codingassignment.dto.TransactionDetailFileReadDTO;

/**
 * DAO to get the child transactions
 *
 * @author dinesh.singla
 *
 */
@Component
public class TransactionDetailDAO extends BaseJsonDAO {
	//cached the map and list so that we dont have to process the list or go to file to get the data again and again
	private Map<Long, List<TransactionDetail>> parentChildMapCache = new HashMap<>();
	private List<TransactionDetail> cache = null;

	private static final Logger logger = LoggerFactory.getLogger(TransactionDetailDAO.class);
	private final ObjectMapper objectMapper;

	public TransactionDetailDAO(ResourceLoader resourceLoader, ObjectMapper objectMapper) {
		super(resourceLoader);
		this.objectMapper = objectMapper;
	}

	/**
	 * Method to get the cached map if already cached otherwise cache it and return that.
	 *
	 * @return map with key as parentId and value as list of transaction details
	 */
	public Map<Long, List<TransactionDetail>> getParentChildTransactionMap() {
		//if cached then return it
		if (!parentChildMapCache.isEmpty()) {
			return parentChildMapCache;
		}

		// get all the child transactions
		List<TransactionDetail> details = get();

		if (CollectionUtils.isEmpty(details)) {
			return parentChildMapCache;
		}

		//process the received child transactions, store it and return it.
		details.forEach(d -> {
			long parentId = d.getParentId();
			if (!parentChildMapCache.containsKey(parentId)) {
				parentChildMapCache.put(parentId, new ArrayList<>());
			}

			parentChildMapCache.get(parentId).add(d);
		});

		return parentChildMapCache;
	}

	/**
	 * Method to get all the child transactions
	 * if the list is cached then that is returned if not then it is cached after reading it from the file
	 * @return the list of the child transactions
	 */
	public List<TransactionDetail> get() {
		//if cached then return it
		if (!CollectionUtils.isEmpty(cache)) {
			return cache;
		}

		// loaded the resource if presernt
		Resource resource = loadResourceFromClasspath("Child");

		if (!resource.exists()) {
			return cache;
		}

		// process the resource and store in cache
		try (InputStream inputStream = resource.getInputStream()) {
			TransactionDetailFileReadDTO fileDTO = objectMapper.readValue(inputStream, TransactionDetailFileReadDTO.class);

			if (fileDTO != null && fileDTO.getData() != null) {
				cache = fileDTO.getData();
			}

		} catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

		return cache;
	}
}
