package com.fdmgroup.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
* This is an entity class for Company objects.
*
*/

@Entity(name="companies")
public class Company {
	
	//could be problem here with its connection to the places class
	@Id
	@SequenceGenerator(name = "COMPANIES", sequenceName = "company_id_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COMPANIES")
	private int company_id;
	
	@Column(name = "company_name")
	private String company_name;
	
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "place_id")
    private Place place;
	
	public Company(){};
	
public Company(String company_name, Place place){
	this.company_name = company_name;
	this.place = place;
	
}

public int getCompany_id() {
	return company_id;
}

public void setCompany_id(int company_id) {
	this.company_id = company_id;
}

public String getCompany_name() {
	return company_name;
}

public void setCompany_name(String company_name) {
	this.company_name = company_name;
}

public Place getPlace() {
	return place;
}

public void setPlace_id(Place place_id) {
	this.place = place_id;
}

}
