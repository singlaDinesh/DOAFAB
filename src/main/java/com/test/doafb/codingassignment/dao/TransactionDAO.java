package com.test.doafb.codingassignment.dao;

import java.io.IOException;
import java.io.InputStream;
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
import com.test.doafb.codingassignment.dao.dto.Transaction;
import com.test.doafb.codingassignment.dto.TransactionFileReadDTO;

/**
 * DAO to get the parent transactions
 *
 * @author dinesh.singla
 *
 */
@Component
public class TransactionDAO extends BaseJsonDAO {
	//cached the map and list so that we dont have to process the list or go to file to get the data again and again
	private Map<Long, Transaction> idToTransactionMapCache = new HashMap<>();
	private List<Transaction> cache = null;

	private static final Logger logger = LoggerFactory.getLogger(TransactionDAO.class);
	private final ObjectMapper objectMapper;


	public TransactionDAO(ResourceLoader resourceLoader, ObjectMapper objectMapper) {
		super(resourceLoader);
		this.objectMapper = objectMapper;
	}

	/**
	 * Method to get the cached map if already cached otherwise cache it and return that.
	 *
	 * @return map with parent id as key and transaction object as values
	 */
	public Map<Long, Transaction> getIdToTransactionMap() {
		//if cached then return it
		if (idToTransactionMapCache != null && !idToTransactionMapCache.isEmpty()) {
			return idToTransactionMapCache;
		}
		// get all the transactions
		List<Transaction> transactions = get();
		if (CollectionUtils.isEmpty(transactions)) {
			return idToTransactionMapCache;
		}
		//process the received transactions, store it and return it.
		transactions.forEach(t -> {
			idToTransactionMapCache.put(t.getId(), t);
		});

		return idToTransactionMapCache;
	}

	/**
	 * Method to get all the transactions
	 * if the list is cached then that is returned if not then it is cached after reading it from the file
	 *
	 * @return the list of the transactions
	 */
	public List<Transaction> get() {
		//if cached then return it
		if (!CollectionUtils.isEmpty(cache)) {
			return cache;
		}

		// loaded the resource if presernt
		Resource resource = loadResourceFromClasspath("Parent");

		if (!resource.exists()) {
			return cache;
		}

		// process the resource and store in cache
		try (InputStream inputStream = resource.getInputStream()) {
			TransactionFileReadDTO fileDTO = objectMapper.readValue(inputStream, TransactionFileReadDTO.class);

			if (fileDTO != null && fileDTO.getData() != null) {
				cache = fileDTO.getData();
			}

		} catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

		return cache;
	}
}
