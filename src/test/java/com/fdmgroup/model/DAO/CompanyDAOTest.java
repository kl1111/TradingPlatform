package com.fdmgroup.model.DAO;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.model.Company;
import com.fdmgroup.model.Place;
import com.fdmgroup.validation.InvalidCompanyException;

public class CompanyDAOTest {

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private CompanyDAO companyDAO;
	private EntityTransaction entityTransaction;
	private Company company;

	@Before
	public void before() {

		entityManagerFactory = Persistence.createEntityManagerFactory("groupproject");
		entityManager = entityManagerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction();
		companyDAO = new CompanyDAO(entityManager);
	}

	@Test
	public void test_SuccessfulConnectionMade() {
		// ARRANGE
		// ACT
		// ASSERT
		assertNotNull(entityTransaction);
	}

	@Test
	public void test_findCompanyWithNull_ReturnsNull() {
		// Arrange

		// Act
		company = companyDAO.findCompany(0);
		// Assert
		assertNull(company);
	}

	@Test
	public void test_findCompanyWithValidNumber_ReturnsCompany() {
		// Arrange

		// Act
		company = companyDAO.findCompany(1);
		// Assert
		assertNotNull(company);
	}
	
	@Test
	public void test_findCompanybyNameWithValidName_ReturnsCompany() {
		// Arrange

		// Act
		company = companyDAO.findCompanyByName("FDM Group");
		// Assert
		assertNotNull(company);
	}
	
	@Test(expected=NoResultException.class)
	public void test_findCompanybyNameWithInvalidName_ReturnsNull() {
		// Act
		company = companyDAO.findCompanyByName("Youtube");
		
	}

	@Test
	public void test_findCompanyWithinvalidNumber_ReturnsNull() {
		// Arrange

		// Act
		company = companyDAO.findCompany(100000000);
		// Assert
		assertNull(company);
	}


	@Test
	public void test_createCompanywithValidCredentials_ReturnsCompany() throws InvalidCompanyException {
		// Arrange

		// Act
		company = companyDAO.addCompany("FDM", entityManager.find(Place.class, 3));
		// Assert
		assertNotNull(company);
	}

	@Test(expected = RollbackException.class)
	public void test_createCompanyWithInvalidName_ReturnsError() {
		// Arrange

		// Act
		entityTransaction.begin();
		Place place = new Place();
		place.setPlace_id(2);
		company = companyDAO.addCompany(
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam eget sem fringilla, aliquet eros ultricies, imperdiet diam. Nunc commodo aliquet vestibulum. Sed pellentesque mattis ipsum, id suscipit nisl tempor eu. Phasellus lobortis, massa et fringilla mattis, tellus quam tempor tortor, et vulputate nulla est nec enim. Proin a ullamcorper nibh. Suspendisse.",
				place);
		entityTransaction.commit();
		// Assert

		assertNull(company);
	}

	@Test(expected = RollbackException.class)
	public void test_createCompanywithInvalidForeignKey_ReturnsError() {
		// Arrange

		// Act
		entityTransaction.begin();
		Place place = new Place();
		place.setPlace_id(420);
		company = companyDAO.addCompany("DankMemes", place);
		entityTransaction.commit();
		// Assert
		assertNotNull(company);
	}

	@Test
	public void test_listCompany_ReturnsNotNull() {
		// Arrange

		// Act
		Company[] actual = companyDAO.listCompanies();
		// Assert
		assertNotNull(actual);
	}

	@Test
	public void test_listCompany_ReturnsCompanyList() {
		// Arrange

		// Act
		Company[] actual = companyDAO.listCompanies();
		// Assert
		assertEquals(16, actual.length);
	}

	@Test
	public void test_removeCompanywithInvalidKey_ReturnsError() {
		// Arrange
		String actual = null;
		// Act
		entityTransaction.begin();
		try {
			companyDAO.removeCompany(-1);
		} catch (InvalidCompanyException e) {
			// TODO Auto-generated catch block
			actual = e.getMessage();
		}
		entityTransaction.commit();
		// Assert

		assertEquals("Company doesn't exist", actual);
	}

	@Test
	public void test_addCompanywithValidKey_addsOneItem() throws InvalidCompanyException {
		// Arrange

		// Act
		entityTransaction.begin();
		int expected = companyDAO.listCompanies().length;
		company = companyDAO.addCompany("FDM", entityManager.find(Place.class, 3));

		int result = companyDAO.listCompanies().length;
		entityTransaction.commit();
		
		entityTransaction.begin();
		companyDAO.removeCompany(company.getCompany_id());
		entityTransaction.commit();

		// Assert

		assertEquals(expected, result - 1);
	}
	
	
	@Test
	public void test_removeCompanywithValidKey_removesOneItem() throws InvalidCompanyException {
		// Arrange

		// Act
		entityTransaction.begin();
		company = companyDAO.addCompany("FDM", entityManager.find(Place.class, 3));
		entityTransaction.commit();
		int expected = companyDAO.listCompanies().length;
		
		entityTransaction.begin();
		companyDAO.removeCompany(company.getCompany_id());
		entityTransaction.commit();
		int result = companyDAO.listCompanies().length;

		// Assert

		assertEquals(expected -1, result);
	}

}
