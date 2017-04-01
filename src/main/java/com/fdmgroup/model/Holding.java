package com.fdmgroup.model;

import javax.persistence.*;

/**
* This is an entity class for Holding objects.
* It should save all the shares a user owns.
*
*/

@Entity(name="holdings")
@IdClass(HoldingConcatenation.class)
public class Holding {
	
	@Id
	@ManyToOne(cascade = CascadeType.PERSIST)	
	@JoinColumn(name="user_id")
	private User user_id;
	
	@Id
	@ManyToOne(cascade = CascadeType.PERSIST)	
	@JoinColumn(name="share_id")
	private Share share_id;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "sell")
	private int sell;
	
	@Transient
	private HoldingConcatenation holdingConcatenation;
	
	public Holding(){}
	
	public Holding(User user, Share share, int quantity, int sell){
		holdingConcatenation = new HoldingConcatenation(user.getUserId(),share.getShare_id());
		this.quantity=quantity;
		this.sell=sell;
		this.share_id=share;
		this.user_id=user;		
	}

	public User getUser_id() {
		return user_id;
	}

	public void setUser_id(User user_id) {
		this.user_id = user_id;
	}

	public Share getShare_id() {
		return share_id;
	}

	public void setShare_id(Share share_id) {
		this.share_id = share_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getSell() {
		return sell;
	}

	public void setSell(int sell) {
		this.sell = sell;
	}

	public HoldingConcatenation getHoldingConcatenation() {
		return holdingConcatenation;
	}

	public void setHoldingConcatenation(HoldingConcatenation holdingConcatenation) {
		this.holdingConcatenation = holdingConcatenation;
	}

}
