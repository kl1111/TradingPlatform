package com.fdmgroup.model.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.fdmgroup.model.Broker;
import com.fdmgroup.model.User;

public class BrokerDAO {
	
	private EntityManager entityManager;
	
	public BrokerDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Broker newBroker(User user, String firstName, String lastName) {
		if (firstName == null || lastName == null)
			return null;
		if (user.getUserId() < 1)
			return null;
		Broker broker = new Broker(user, firstName, lastName);
		entityManager.persist(broker);
		return broker;
	}
	
	@SuppressWarnings("unchecked")
	public List<Broker> listBrokers() {
		List<Broker> brokers = new ArrayList<Broker>();
		brokers = entityManager.createNativeQuery("SELECT broker_id, user_fk, first_name, last_name FROM brokers", Broker.class).getResultList();
		return brokers;
	}
	
	public Broker removeBroker(Broker broker) {
		if (broker == null)
			return null;
		entityManager.remove(broker);
		return broker;
	}
	
	public Broker findBroker(int id) {
		if (id == 0)
			return null;
		return entityManager.find(Broker.class, id);
	}

}
