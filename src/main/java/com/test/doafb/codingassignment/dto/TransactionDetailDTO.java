package com.test.doafb.codingassignment.dto;

import java.io.Serializable;

/**
 * Details for all the child transactions
 *
 * @author dinesh.singla
 *
 */
public class TransactionDetailDTO implements Serializable {

	private static final long serialVersionUID = -5383336865353895025L;

	private long id;

	private String sender;

	private String receiver;

	private double totalAmount;

	private double paidAmount;

	public TransactionDetailDTO() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}
}
