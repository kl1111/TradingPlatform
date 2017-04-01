package com.fdmgroup.model.DAO;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.NoResultException;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.model.Place;
import com.fdmgroup.model.DAO.PlaceDAO;
import com.fdmgroup.validation.PlaceException;

public class PlaceDAOTest {

	PlaceDAO placeDAO;
	String[][] oldPlaces;
	int entriesBefore;

	@Before
	public void before() {
		placeDAO = new PlaceDAO();
		entriesBefore = placeDAO.list().size();
	}

	@Test
	public void test_insert() throws PlaceException {
		PlaceDAO placeDAO = new PlaceDAO();
		placeDAO.insert("Peking", "China");
		assertEquals(entriesBefore + 1, placeDAO.list().size());
	}

	@Test(expected = PlaceException.class)
	public void test_insert_throwsException_WhenNoCity() throws PlaceException {
		placeDAO.insert("", "United Kingdom");
	}

	@Test(expected = PlaceException.class)
	public void test_insert_throwsException_WhenNoCity2() throws PlaceException {
		PlaceDAO placeDAO = new PlaceDAO();
		placeDAO.insert(null, "United Kingdom");
	}

	@Test(expected = PlaceException.class)
	public void test_insert_throwsException_WhenNoCountry() throws PlaceException {
		PlaceDAO placeDAO = new PlaceDAO();
		placeDAO.insert("London", "");
	}

	@Test(expected = PlaceException.class)
	public void test_insert_throwsException_WhenNoCountry2() throws PlaceException {
		placeDAO.insert("London", null);
	}

	@Test
	public void test_list_containsTokyoAndParis() throws PlaceException {
		List<Place> places = placeDAO.list();
		boolean tokyo = false;
		boolean paris = false;
		for (Place place : places) {
			if (place.getCity() != null && place.getCity().equals("Tokyo"))
				tokyo = true;
			if (place.getCity() != null && place.getCity().equals("Paris"))
				paris = true;
		}
		assertTrue(tokyo);
		assertTrue(paris);
	}

	@Test
	public void test_update_londonToStockholm() throws PlaceException {
		List<Place> places = placeDAO.list();
		for (Place place : places) {
			if (place.getCity() != null && place.getCity().equals("London")) {
				placeDAO.update(place, "Stockholm", "Sweden");
			}
		}
		int amountOfLondons = 0;
		List<Place> places2 = placeDAO.list();
		for (Place place : places2) {
			if (place.getCity() != null && place.getCity().equals("London")) {
				amountOfLondons++;
			}
		}
		assertEquals(0, amountOfLondons);
	}

	@Test
	public void test_update_3toLA() throws PlaceException {

		placeDAO.update(3, "Los Angeles", "USA");

		int amountOfNewYorks = 0;
		List<Place> places = placeDAO.list();
		for (Place place : places) {
			if (place.getCity() != null && place.getCity().equals("New York")) {
				amountOfNewYorks++;
			}
		}
		assertEquals(0, amountOfNewYorks);
	}

	@Test(expected = NoResultException.class)
	public void test_removeCorrectlyRemovesPlace() throws PlaceException {
		// Act
		placeDAO.insert("Hanoi", "Vietnam");
		Place place = placeDAO.findPlace("Hanoi");
		assertNotNull(place);
		placeDAO.remove(place);

		// Assert
		placeDAO.findPlace("Hanoi");
	}

}
