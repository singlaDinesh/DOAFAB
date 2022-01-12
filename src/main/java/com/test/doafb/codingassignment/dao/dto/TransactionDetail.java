package com.test.doafb.codingassignment.dao.dto;

import java.io.Serializable;

/**
 * Object class for child transactions
 * This implements Comparable as we want the default sorting to be on id
 *
 * @author dinesh.singla
 *
 */
public class TransactionDetail implements Serializable, Comparable<TransactionDetail> {

	private static final long serialVersionUID = -5383336865353895025L;

	private long id;

	private long parentId;

	private double paidAmount;

	public TransactionDetail() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	@Override
	public int compareTo(TransactionDetail o) {
		return (int) (id - o.getId());
	}
}
