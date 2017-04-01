package com.fdmgroup.model.DAO;

import java.util.List;

import javax.persistence.EntityManager;

import com.fdmgroup.model.ExchangeRate;

public class ExchangeRateDAO {
	
	private EntityManager entityManager;
	
	public ExchangeRateDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public List<ExchangeRate> listAllRates() {
		@SuppressWarnings("unchecked")
		List<ExchangeRate> rates = entityManager.createNativeQuery("SELECT currency_id, exchange_rate, issue_day FROM exchange_rates", ExchangeRate.class).getResultList();
		return rates;
	}

	public List<ExchangeRate> listCurrentRates() {
		@SuppressWarnings("unchecked")
		List<ExchangeRate> rates = entityManager.createNativeQuery("SELECT currency_id, exchange_rate, issue_day FROM exchange_rates WHERE issue_day LIKE SYSDATE", ExchangeRate.class).getResultList();
		return rates;
	}

	public double convert(int idFrom, int idTo, double input) {
		if (idFrom == 0 || idTo == 0 || input == 0)
			return 0.0;
		List<ExchangeRate> rates = listCurrentRates();
		double fromRate = 0;
		double toRate = 0;
		for (ExchangeRate rate : rates) {
			if (rate.getCurrency_id().getCurrency_id() == idFrom)
				fromRate = rate.getExchange_rate();
			if (rate.getCurrency_id().getCurrency_id() == idTo)
				toRate = rate.getExchange_rate();
		}
		if (fromRate == 0 || toRate == 0)
			return 0;
		double exRate = toRate * 1/fromRate;
		return input * exRate;
	}

}
