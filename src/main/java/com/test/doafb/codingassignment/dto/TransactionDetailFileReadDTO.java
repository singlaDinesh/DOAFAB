package com.test.doafb.codingassignment.dto;

import java.io.Serializable;
import java.util.List;

import com.test.doafb.codingassignment.dao.dto.TransactionDetail;

public class TransactionDetailFileReadDTO implements Serializable {

	private static final long serialVersionUID = 1092932479126445105L;

	private List<TransactionDetail> data;

	public TransactionDetailFileReadDTO() {
		super();
	}

	public List<TransactionDetail> getData() {
		return data;
	}

	public void setData(List<TransactionDetail> data) {
		this.data = data;
	}

}
