package com.fdmgroup.model;

import java.io.Serializable;

/**
* This is a a concatenation class.
*
*/

public class SharePriceConcatenation implements Serializable {

	private static final long serialVersionUID = 1L;
	

	
	private int share_id;
	private long time;

	public SharePriceConcatenation() {}

	public SharePriceConcatenation(int share_id, long time) {
		this.share_id = share_id;
		this.time = time;
	}
	
	public int getShare_id() {
		return share_id;
	}
	public long getTime() {
		return time;
	}
	public void setShare_id(int share_id) {
		this.share_id = share_id;
	}
	public void setTime(long time) {
		this.time = time;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + share_id;
		result = prime * result + (int) (time ^ (time >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SharePriceConcatenation other = (SharePriceConcatenation) obj;
		if (share_id != other.share_id)
			return false;
		if (time != other.time)
			return false;
		return true;
	}
	
}
