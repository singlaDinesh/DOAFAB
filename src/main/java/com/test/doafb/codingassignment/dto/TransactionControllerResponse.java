package com.test.doafb.codingassignment.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Object class for response. it contains the data field for data to be responded and totalRows field for data that is paginated
 *
 * @author dinesh.singla
 *
 * @param <V> type of response we want to return
 */
public class TransactionControllerResponse<V> implements Serializable {

	private static final long serialVersionUID = 8932294455766510726L;

	public List<V> beans;

    public Integer totalRows;

	public TransactionControllerResponse(List<V> beans, Integer totalRows) {
		this.beans = beans;
		this.totalRows = totalRows;
	}

	public List<V> getBeans() {
		return beans;
	}

	public void setBeans(List<V> beans) {
		this.beans = beans;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}
}
