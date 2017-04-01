package com.fdmgroup.model;

import java.io.Serializable;

public class HoldingConcatenation implements Serializable {

	/**
	* This is a concatenation class.
	*
	*/
	
	private static final long serialVersionUID = -245217487942322793L;
	

	private int user_id;
	private int share_id;
	
	public HoldingConcatenation() {}

	public HoldingConcatenation(int user_id, int share_id) {
		this.share_id = share_id;
		this.user_id = user_id;
	}
	
	public int getShare_id() {
		return share_id;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setShare_id(int share_id) {
		this.share_id = share_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + share_id;
		result = prime * result + user_id;
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
		HoldingConcatenation other = (HoldingConcatenation) obj;
		if (share_id != other.share_id)
			return false;
		if (user_id != other.user_id)
			return false;
		return true;
	}


	
	
}
