package com.bom.utils;

public class DataResult {
	private int errno;
	private Object data;
	private String error;
	
	public DataResult() {
	}
	
	public DataResult(Object data) {
		this.errno = 0;
		this.data = data;
	}
	public DataResult(int errno, String error) {
		this.errno = errno;
		this.error = error;
	}
	
	
	public static DataResult failure(Integer code, String message) {
		return new DataResult(code,message);
	}
	
	
	
	public static DataResult success(Object data) {
		DataResult dataResult=new DataResult(data);
		dataResult.setErrno(200);
		return dataResult;
	}
	

	public int getErrno() {
		return errno;
	}

	public void setErrno(int errno) {
		this.errno = errno;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
