package com.test.doafb.codingassignment.dto;

import java.io.Serializable;
import java.util.List;

import com.test.doafb.codingassignment.dao.dto.Transaction;

public class TransactionFileReadDTO implements Serializable {

	private static final long serialVersionUID = 6663715986428303106L;

	private List<Transaction> data;

	public TransactionFileReadDTO() {
		super();
	}

	public List<Transaction> getData() {
		return data;
	}

	public void setData(List<Transaction> data) {
		this.data = data;
	}

}
