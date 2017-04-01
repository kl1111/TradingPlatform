package com.fdmgroup.model;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
* This is an entity class for SharePrice objects.
* They are used to price everything.
*
*/

	@Entity(name="shares_prices")
	@IdClass(SharePriceConcatenation.class)
	public class SharePrice implements Comparator<SharePrice> {
	
	@Id
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="share_id")
	private Share share_id;
	
	@Id
	@Column(name = "time_start")
    private long time;
	
	@Column(name="price")
	private double price;
	
	@Transient
	private SharePriceConcatenation sharePriceConcatenation;
	
	public SharePrice(){}
	
	public SharePrice(Share share_id, long time, double price){
		sharePriceConcatenation = new SharePriceConcatenation();
		sharePriceConcatenation.setTime(time);
		sharePriceConcatenation.setShare_id(share_id.getId());
		this.share_id = share_id;
		this.time = time;
		this.price = price;
	}

	public Share getShare() {
		return share_id;
	}
	public void setShare(Share share) {
		this.share_id = share;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	public String printTime() {
		Date date = new Date(time);     //	 this is for displaying a date
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		//SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		String dateString = dateFormat.format(date);
		return dateString;
	}
	
	public String toString() {
		return share_id + " >> " + time + " >> " + price;
	}

	public int compare(SharePrice sp1, SharePrice sp2) {
		return (int) ((sp1.getTime() - sp2.getTime())/10000);
	}
}
