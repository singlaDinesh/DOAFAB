package com.test.doafb.codingassignment.dto;

import java.io.Serializable;

/**
 * DTO for all the transaction details as required
 * @author dinesh.singla
 *
 */
public class TransactionDTO implements Serializable {

	private static final long serialVersionUID = -8153021532039616700L;

	private long id;

	private String sender;

	private String receiver;

	private double totalAmount;

	private double totalPaid;

	public TransactionDTO() {
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

	public double getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(double totalPaid) {
		this.totalPaid = totalPaid;
	}
}
