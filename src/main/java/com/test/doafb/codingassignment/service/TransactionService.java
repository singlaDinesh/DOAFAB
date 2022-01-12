package com.test.doafb.codingassignment.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.test.doafb.codingassignment.dao.TransactionDAO;
import com.test.doafb.codingassignment.dao.TransactionDetailDAO;
import com.test.doafb.codingassignment.dao.dto.Transaction;
import com.test.doafb.codingassignment.dao.dto.TransactionDetail;
import com.test.doafb.codingassignment.dto.OrderBy;
import com.test.doafb.codingassignment.dto.TransactionDTO;
import com.test.doafb.codingassignment.dto.TransactionDetailDTO;
import com.test.doafb.codingassignment.dto.TransactionControllerResponse;

/**
 * Service to handle the transaction APIs
 *
 * @author dinesh.singla
 *
 */
@Service
public class TransactionService {

	//injected beans
	private final TransactionDAO transactionDAO;
	private final TransactionDetailDAO transactionDetailDAO;

	public TransactionService(TransactionDAO transactionDAO,
			TransactionDetailDAO transactionDetailDAO) {
		super();
		this.transactionDAO = transactionDAO;
		this.transactionDetailDAO = transactionDetailDAO;
	}

	/**
	 * Method to get the transactions with the functionality of pagination
	 * Order by is only enabled on id of transaction
	 *
	 * @param start from where you want to get the next two elements
	 * @param orderBy default is ASC
	 * @return Two transactions from start. if start is at the last transaction then only that is returned and if start is more than size then empty list is returned.
	 */
	public TransactionControllerResponse<TransactionDTO> getTransactions(int start, OrderBy orderBy) {
		//All the transactions are get and added to a new list as we will be modifying the data in the list and we want to lose the data in the cached list
		List<Transaction> transactions = new ArrayList<>(transactionDAO.get());

		//if no data is present or start is more than the size then empty list is returned
		if (CollectionUtils.isEmpty(transactions) || start > transactions.size()) {
			return new TransactionControllerResponse<>(new ArrayList<>(), 0);
		}

		//this is stored to respond with total rows before applying pagination
		int size = transactions.size();

		transactions = handlePaginationForParent(transactions, start - 1, orderBy);

		List<TransactionDTO> dtos = new ArrayList<>();
		Map<Long, List<TransactionDetail>> map = transactionDetailDAO.getParentChildTransactionMap();

		//Creating the dtos
		dtos = transactions.stream().map(t -> {
			TransactionDTO dto = new TransactionDTO();
			dto.setId(t.getId());
			dto.setReceiver(t.getReceiver());
			dto.setSender(t.getSender());
			dto.setTotalAmount(t.getTotalAmount());

			if (map.containsKey(t.getId()) && !CollectionUtils.isEmpty(map.get(t.getId()))) {
				//calculating the paid amount
				dto.setTotalPaid(map.get(t.getId()).stream().map(TransactionDetail :: getPaidAmount)
						.collect(Collectors.summingDouble(Double :: doubleValue)));
			}
			return dto;
		}).collect(Collectors.toList());

		return new TransactionControllerResponse<>(dtos, size);
	}

	/**
	 * Method to get the child transactions of parent transactions
	 * Order of child transactions will be ASC by their transaction id
	 *
	 * @param parentId for which we want the details
	 * @return All the child transactions with details
	 */
	public TransactionControllerResponse<TransactionDetailDTO> getTransactionDetails(long parentId) {
		// all the child transaction beans for given parent id
		List<TransactionDetail> transactionDetails = transactionDetailDAO
				.getParentChildTransactionMap().get(parentId);

		//if the transaction details does not exist then return empty list
		if (CollectionUtils.isEmpty(transactionDetails)) {
			return new TransactionControllerResponse<>(new ArrayList<>(), 0);
		}

		// sort asc
		Collections.sort(transactionDetails);
		Map<Long, Transaction> transactionMap = transactionDAO.getIdToTransactionMap();

		//populate the dto list with all the required details
		List<TransactionDetailDTO> dtos = transactionDetails.stream().map(d -> {
			TransactionDetailDTO dto = new TransactionDetailDTO();
			dto.setId(d.getId());
			dto.setPaidAmount(d.getPaidAmount());
			Transaction t = transactionMap.get(d.getParentId());
			dto.setTotalAmount(t.getTotalAmount());
			dto.setSender(t.getSender());
			dto.setReceiver(t.getReceiver());
			return dto;
		}).collect(Collectors.toList());

		return new TransactionControllerResponse<>(dtos, dtos.size());
	}

	/**
	 *The function to paginate the transactions
	 *
	 * @param transactions on which you want to apply the pagination
	 * @param start from where you want to get the next two elements
	 * @param orderBy ASC or DES default is ASC
	 * @return The paginated transaction data. Fixed the size of paginated data by 2 as mentioned in the requirement doc
	 */
	private List<Transaction> handlePaginationForParent(List<Transaction> transactions,
			int start, OrderBy orderBy) {
		// default ordering
		if (orderBy == null) {
			orderBy = OrderBy.ASC;
		}

		//ordering the data with respect to orderBy
		if (OrderBy.ASC.equals(orderBy)) {
			Collections.sort(transactions);
		} else {
			Collections.sort(transactions, Collections.reverseOrder());
		}

		//Sublisting the data
		if (start + 2 > transactions.size()) {
			transactions = transactions.subList(start, transactions.size());
		} else {
			transactions = transactions.subList(start, start + 2);
		}

		return transactions;
	}
}
