package com.fdmgroup.model;

import javax.persistence.*;

@Entity(name="brokers")
public class Broker {
	
	/**
	 * This is an entity class for Broker objects.
	 */
	
	@Id
	@SequenceGenerator(name = "brokers", sequenceName = "broker_id_seq", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="brokers")
	private int broker_id;
	
	@ManyToOne(cascade = CascadeType.PERSIST)	
	@JoinColumn(name="user_fk")
	private User userFk;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	public Broker() {
		
	}
	
	public Broker(User userFk, String firstName, String lastName) {
		this.userFk = userFk;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getBroker_id() {
		return broker_id;
	}

	public void setBroker_id(int broker_id) {
		this.broker_id = broker_id;
	}

	public User getUserFk() {
		return userFk;
	}

	public void setUserFk(User userFk) {
		this.userFk = userFk;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	

}
