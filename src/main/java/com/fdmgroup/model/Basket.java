package com.fdmgroup.model;

public class Basket {

	private int share_id;
	private int quantity;
	
	public Basket(){
	}
	public Basket(int share_id, int quantity){
		this.share_id=share_id;
		this.quantity=quantity;
	}
	public int getShare_id() {
		return share_id;
	}
	public void setShare_id(int share_id) {
		this.share_id = share_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
