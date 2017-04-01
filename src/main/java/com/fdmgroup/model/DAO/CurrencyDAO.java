package com.fdmgroup.model.DAO;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;
import com.fdmgroup.model.Currency;

public class CurrencyDAO {
	private EntityManager entityManager;
	private Currency currency = new Currency();
	private List<Currency> listOfCurrencies = new ArrayList<Currency>();
	static Logger log = Logger.getLogger(CurrencyDAO.class);

	public CurrencyDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Currency findCurrency(int currency_id) {
		if (currency_id != 0) {
			currency = entityManager.find(Currency.class, currency_id);
			if (currency != null) {
				log.info("found currency: " + currency.getCurrency_id());
				return currency;
			} else
				log.error("Currency not Found");
			return null;
		} else
			log.error("No Input");
		return null;
	}

	public List<Currency> listCurrencies() {
		listOfCurrencies = entityManager.createQuery("SELECT e FROM currencies e", Currency.class).getResultList();
		return listOfCurrencies;
	}

	public Currency createCurrency(Currency currency) {
		String symbol = currency.getSymbol();
		String name = currency.getName();
		Currency foundCurrency = null;
		if (symbol != null && name != null) {
			try {
				foundCurrency = (Currency) entityManager.createNativeQuery(
						"SELECT currency_id,name,symbol FROM currencies where name= " + "'" + name + "'",
						Currency.class).getSingleResult();
			} catch (NoResultException e) {

			}
			if (foundCurrency == null) {
				entityManager.persist(currency);
				log.info("Inserted currency: " + currency.getName() + " into the table.");
				return currency;
			} else
				log.error("Currency already Exists");
			return null;
		} else
			log.error("Value is null");
		return null;
	}

	public void deleteCurrency(int currency_id) {
		currency = findCurrency(currency_id);
		if (currency != null) {
			entityManager.remove(currency);
			log.info("Removed currency: " + currency.getCurrency_id() + " from the table.");
		} else
			log.error("Currency not removed");
	}

	public Currency updateCurrencyName(Currency currency) {
		Currency foundCurrency = null;
		if (currency == null) {
			log.error("No Currency Entered");
			return null;
		}
		String name = currency.getName();
		String symbol = currency.getSymbol();
		try {
			foundCurrency = (Currency) entityManager.createNativeQuery(
					"SELECT currency_id,name,symbol FROM currencies where symbol= " + "'" + symbol + "'",
					Currency.class).getSingleResult();
		} catch (NoResultException e) {
			log.error("currency not in database");
			return null;
		}
		foundCurrency.setName(name);
		entityManager.merge(foundCurrency);
		log.info("name changed");
		return foundCurrency;
	}

	public Currency updateCurrencySymbol(Currency currency) {
		if (currency == null) {
			log.error("No Currency Entered");
			return null;
		}
		String name = currency.getName();
		String symbol = currency.getSymbol();
		Currency foundCurrency = null;
		try {
			foundCurrency = (Currency) entityManager
					.createNativeQuery("SELECT currency_id,name,symbol FROM currencies where name= " + "'" + name + "'",
							Currency.class)
					.getSingleResult();
		} catch (NoResultException e) {
			log.error("currency not in database");
			return null;
		}
		if (symbol != null) {
			foundCurrency.setSymbol_id(symbol);
			log.error("Symbol changed");
		}
		entityManager.merge(foundCurrency);
		return foundCurrency;

	}

}
