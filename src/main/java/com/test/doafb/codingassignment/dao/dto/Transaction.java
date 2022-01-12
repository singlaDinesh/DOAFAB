package com.test.doafb.codingassignment.dao.dto;

import java.io.Serializable;

/**
 * Object class to store the data for parent transaction
 * This implements Comparable as we want sorting only on id
 *
 * @author dinesh.singla
 *
 */
public class Transaction implements Serializable, Comparable<Transaction> {

	private static final long serialVersionUID = -8153021532039616700L;

	private long id;

	private String sender;

	private String receiver;

	private double totalAmount;

	public Transaction() {
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

	@Override
	public int compareTo(Transaction o) {
		return (int) (id - o.getId());
	}
}
