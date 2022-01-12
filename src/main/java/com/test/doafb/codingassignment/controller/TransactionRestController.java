package com.test.doafb.codingassignment.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.doafb.codingassignment.dto.OrderBy;
import com.test.doafb.codingassignment.dto.TransactionDTO;
import com.test.doafb.codingassignment.dto.TransactionDetailDTO;
import com.test.doafb.codingassignment.dto.TransactionControllerResponse;
import com.test.doafb.codingassignment.service.TransactionService;

/**
 * The Rest Controller to get the transactions info.
 *
 * @author dinesh.singla
 *
 */
@RestController
@RequestMapping(path = "transaction", produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionRestController {

	private final TransactionService service;

	public TransactionRestController(TransactionService service) {
		super();
		this.service = service;
	}

	/**
	 * The API to get the transactions with the functionality of pagination.
	 * Fixed the size of paginated data by 2 as mentioned in the requirement document
	 * Order by is only enabled on id of transaction
	 *
	 * @param start from where you want to get the next two elements
	 * @param orderBy ASC or DES default is ASC
	 * @return Two transactions from start. if start is at the last transaction
	 * then only that is returned and if start is more than size then empty list is returned.
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public TransactionControllerResponse<TransactionDTO> getTransactions(@RequestParam int start,
    		@RequestParam(required = false) OrderBy orderBy) {
        return service.getTransactions(start, orderBy);
    }

	/**
	 * The API to get the transaction details
	 * The data will be returned in ASC order by child transaction id
	 *
	 * @param parentId transaction id for which we want to get all its child transactions
	 * @return returns all the child transactions of the parent transaction
	 */
	@GetMapping(value = "/details", produces = MediaType.APPLICATION_JSON_VALUE)
	public TransactionControllerResponse<TransactionDetailDTO> getTransactionDetails(@RequestParam long parentId) {
		return service.getTransactionDetails(parentId);
	}
}
