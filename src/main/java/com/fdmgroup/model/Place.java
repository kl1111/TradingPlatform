package com.fdmgroup.model;

import javax.persistence.*;

/**
* This is an entity class for Place objects.
*
*/

@Entity(name="places")
public class Place {
//Could be problem here with database conection to the company table
	@Id
	@SequenceGenerator(name="place_id", sequenceName="place_id_seq", allocationSize=1, initialValue=0)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="place_id")
	@Column(name="place_id") // attributes
	private int place_id;
	@Column(name="city")
	private String city;
	@Column(name="country")
	private String country;
//	@OneToMany( targetEntity=Company.class )
//    private List companyList;
	
	public Place() {} // constructors
	
	public Place(String city, String country) {
		this.setCity(city);
		this.setCountry(country);
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getId() {
		return place_id;
	}

	public int getPlace_id() {
		return place_id;
	}

	public void setPlace_id(int place_id) {
		this.place_id = place_id;
	}

}
