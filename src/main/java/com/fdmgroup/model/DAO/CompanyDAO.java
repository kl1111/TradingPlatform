package com.fdmgroup.model.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import org.apache.log4j.Logger;

import com.fdmgroup.model.Company;
import com.fdmgroup.model.Place;
import com.fdmgroup.validation.InvalidCompanyException;

public class CompanyDAO {

	private EntityManager entitymanager;
	static Logger log = Logger.getLogger(CompanyDAO.class);
	
	public CompanyDAO(EntityManager entityManager) {
		this.entitymanager = entityManager;
	}

	public Company addCompany(String company_name, Place place) {
		Company company = new Company(company_name, place);
		if(entitymanager.find(Place.class, place.getId())==null){
			throw new RollbackException();
		}
		entitymanager.persist(company);
		log.info("Inserted company: " + company.getCompany_name() + " into the table.");

		return company;

	}

	public Company findCompany(int company_id) {
		return entitymanager.find(Company.class, company_id);

	}

	public void removeCompany(int company_id) throws InvalidCompanyException {
		Company searchedCompany = findCompany(company_id);
		if (searchedCompany != null) {
			log.info("Removed company: " + searchedCompany.getCompany_name() + " from the table.");
			entitymanager.remove(searchedCompany);
			
		}
		else 
			throw new InvalidCompanyException("Company doesn't exist");
	}
	
	public Company findCompanyByName (String company_name){
		Query findCompany = entitymanager.createNativeQuery("SELECT company_id, company_name, place_id FROM companies WHERE company_name =" + "'" + company_name + "'", Company.class);
		Company company = (Company) findCompany.getSingleResult();
		
		return company;
	}
	
	@SuppressWarnings("unchecked")
	public Company[] listCompanies(){
		
		List<Company> CompaniesList = new ArrayList<Company>();
		Query findCompanies = entitymanager.createNativeQuery("SELECT company_id, company_name, place_id FROM companies", Company.class);
		CompaniesList = findCompanies.getResultList();
		Company[] result = new Company[CompaniesList.size()];
		CompaniesList.toArray(result);
		
		return result;
		
	}

}
