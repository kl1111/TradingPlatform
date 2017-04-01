package com.fdmgroup.model.DAO;

import java.util.List;

import javax.persistence.*;

import org.apache.log4j.Logger;

import com.fdmgroup.model.Place;
import com.fdmgroup.validation.PlaceException;


public class PlaceDAO {
	
	static Logger log = Logger.getLogger(PlaceDAO.class);
	EntityManagerFactory entityManagerFactory;
	EntityManager entityManager;
	EntityTransaction entityTransaction;
	
	public PlaceDAO() {
		entityManagerFactory = Persistence.createEntityManagerFactory("groupproject"); // DB stuff
		entityManager = entityManagerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction();
	}
	
	public void insert(String city, String country) throws PlaceException {
		if (city == null || country == null || city == "" || country == "")
			throw new PlaceException("PlaceDAO insert method: invalid Parameters given.");
		Place place = new Place(city, country);
		entityTransaction.begin();
		entityManager.persist(place);
		entityTransaction.commit();
		log.info("Inserted a new place: " + city + " in " + country + " with place_id = " + place.getId());
	}
	
	public List<Place> list() {
		TypedQuery<Place> query1 = entityManager.createQuery("SELECT p FROM places p", Place.class);
		List<Place> results = query1.getResultList();
		log.info("Searched for places. Found " + results.size() + " entries.");
		return results;
	}
	
	public void update(int place_id, String city, String country) throws PlaceException {
		if (city == null || country == null || city == "" || country == "")
			throw new PlaceException("PlaceDAO insert method: invalid Parameters given.");
		Place place = entityManager.find(Place.class, place_id);
		if (place != null) {
			entityTransaction.begin();
			place.setCity(city);
			place.setCountry(country);
			entityTransaction.commit();
		}
	}
	
	public void update(Place place, String city, String country) throws PlaceException {
		if (city == null || country == null || city == "" || country == "")
			throw new PlaceException("PlaceDAO insert method: invalid Parameters given.");
		if (place != null) {
			entityTransaction.begin();
			place.setCity(city);
			place.setCountry(country);
			entityTransaction.commit();
		}
	}
	
	public void remove(int place_id) {
		Place place = entityManager.find(Place.class, place_id);
		if (place != null) {
			entityTransaction.begin();
			entityManager.remove(place);
			entityTransaction.commit();
		}
	}
	
	public void remove(Place place) {
		if (place != null) {
			entityTransaction.begin();
			entityManager.remove(place);
			entityTransaction.commit();
		}
	}
		
	public Place findPlace(String placeName){
		
		Query findPlace = entityManager.createNativeQuery("SELECT place_id, city, country FROM places WHERE city =" + "'" + placeName + "'", Place.class);
		Place place = (Place) findPlace.getSingleResult();
		
		return place;
				
	}
	
}
