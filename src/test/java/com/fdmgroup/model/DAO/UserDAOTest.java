package com.fdmgroup.model.DAO;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.model.User;
import com.fdmgroup.model.DAO.UserDAO;
import com.fdmgroup.validation.RegistrationException;
import com.fdmgroup.validation.loginException;

public class UserDAOTest {

	public EntityManagerFactory entityManagerFactory;
	public EntityManager entityManager;
	public EntityTransaction entityTransaction;
	public UserDAO userDAO;
	public static final int ID = 1;
	public User user;
	
	@Before
	public void before() {
		entityManagerFactory = Persistence.createEntityManagerFactory("groupproject");
		entityManager = entityManagerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction();
		userDAO = new UserDAO(entityManager);
		user = new User("username", "password", "firstName", "lastName", "email", "Regular User");
		
	}
	
	@After
	public void after() throws loginException {
		
		
		entityManager.close();
		entityManagerFactory.close();
	}
	
	@Test(expected=loginException.class)
	public void test_isUsername_returnsFalse_WhenNullEntered() throws loginException {
		User actual = userDAO.findUsername(null);
		assertEquals(null, actual);
	}
	
	@Test
	public void test_isUsername_returnsTrue_WhenValidUsernameInput() throws loginException {
		User actual = userDAO.findUsername("jacksond80");
		assertNotNull(actual);
	}
	
	@Test
	public void test_isUsername_returnsfalse_WhenInvalidUsernameInput() throws loginException {
		String actual = null;
		try {
			 userDAO.findUsername("username1");
		} catch (Exception e) {
			actual = e.getMessage();
		}
		assertEquals("<a href='register'>Invalid username, do you want to register?</a>",actual);
	}
	
	@Test
	public void test_getUserId_returnsMinusOne_WhenCalledWithNullUsername() {
		User actual = null;
		try {
			actual = userDAO.findUserID((Integer) null);
		} catch (Exception e) {
			// TODO: handle exception
		}
		assertNull(actual);
	}
	
	@Test
	public void test_getUserId_returnsMinusOne_WhenCalledWithInvalidUsername() {
		User actual = userDAO.findUserID(420);
		assertNull(actual);
	}
	
	@Test
	public void test_getUserId_ReturnsCorrectId_WhenValidUsernameInput() {
		User actual = userDAO.findUserID(1);
		assertNotNull(actual);
	}
	
	
	public void test_findUser_ReturnsNull_WhenNullInput() throws loginException {
		User actual = userDAO.findUser(null);
		assertNull(actual);
	}
	
	@Test
	public void test_findUser_ReturnsNull_WhenInvalidUsernameInput() {
		String actual = null;
		try {
			userDAO.findUser("username");
		} catch (loginException e) {
			// TODO Auto-generated catch block
			actual = e.getMessage();
		}
		assertEquals("Username does not exist", actual);
	}
	
	@Test
	public void test_findUser_ReturnsCorrectUser_WhenValidUsernameInput() throws loginException {
		User actual = userDAO.findUser("ml404");
		assertEquals("password", actual.getPassword());
		assertEquals("Layton", actual.getLastname());
	}
	
	@Test
	public void test_getAllUsers_ReturnsListOfSize22_WhenCalled() {
		int actual = userDAO.getAllNonDeletedUsers().size();
		assertEquals(21, actual);
	}
	
	@Test
	public void test_getAllActiveUsers_ReturnsListOfSize22_WhenCalled() {
		int actual = userDAO.getAllActiveUsers().size();
		assertEquals(21, actual);
	}
	
	
	@Test
	public void test_listAllAdmins_ReturnsListOfSizeSix_WhenCalled() {
		int actual = userDAO.getAllAdmins().size();
		assertEquals(6, actual);
	}
	
	@Test
	public void test_listBrokers_ReturnsListOfSizeFifteen_WhenCalled() {
		int actual = userDAO.getAllBrokers().size();
		assertEquals(15, actual);
	}
	
	@Test
	public void test_listRegularUser_ReturnsListOfSizeZero_WhenCalled() {
		int actual = userDAO.getAllRegularUsers().size();
		assertEquals(0, actual);
	}
	
	@Test
	public void test_listDeletedUser_ReturnsListOfSizeZero_WhenCalled() {
		int actual = userDAO.getAllDeletedUsers().size();
		assertEquals(0, actual);
	}
	
	@Test
	public void test_listBannedUsers_ReturnsListOfSizeZero_WhenCalled() {
		int actual = userDAO.getAllBannedUsers().size();
		assertEquals(0, actual);
	}
	
	@Test
	public void test_createUser_ReturnsException_WhenAnyInputIsNull() throws RegistrationException {
		User actual = userDAO.createUser(null, null, null, null, null, null);
		assertEquals(null, actual);
	}
	
	@Test
	public void test_createUser_ReturnsNull_WhenExistingUsernameIsEntered() {
		String actual = null;
		try {
			 userDAO.createUser("jacksond80", "password", "firstName", "lastName", "email", "Admin");
		} catch (RegistrationException e) {
			actual = e.getMessage();
		}
		assertEquals("Username is already in use", actual);
	}
	
	@Test
	public void test_createUser_ReturnsUser_WhenValidInput(){
		User actual = null;
		try {
			actual = userDAO.createUser("username", "password", "firstName", "lastName", "email", "Regular User");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(actual);
	}
	
	@Test
	public void test_deleteUser_ReturnsCorrectErrorMessage_WhenNullInput() throws loginException {
		String result = null;
		try {
			 userDAO.deleteUser(null);
		} catch (Exception e) {
			result = e.getMessage();
		}
		assertEquals("Username does not exist", result);
	}
	
	@Test
	public void test_DeleteUser_ReturnsOne_WhenValidInput() throws loginException {
		entityTransaction.begin();
		int actual = userDAO.deleteUser("jacksond80");
		assertEquals(1, actual);
	}
	
	@Test
	public void test_login_returnsNull_WhenNullInput() throws loginException {
		String actual = null;
		try {
		 userDAO.login(null, null);
		} catch (Exception e) {
			actual = e.getMessage();
		}
		assertEquals("<a href='register'>Invalid username, do you want to register?</a>", actual);
	}
	
	@Test
	public void test_login_returnsInvalidUsername_WhenInvalidUsernameInput() {
		String actual = null;
		try {
			userDAO.login("username", "password");
		} catch (loginException e) {
			actual = e.getMessage();
		}
		assertEquals("<a href='register'>Invalid username, do you want to register?</a>", actual);
	}
	
	@Test
	public void test_login_returnsIncorrectPassword_WhenIncorrectPasswordInput() {
		String actual = null;
		try {
			userDAO.login("jacksond80", "notpassword");
		} catch (Exception e) {
			actual = e.getMessage();
		}
		assertEquals("Password is incorrect", actual);
	}
	
	@Test
	public void test_login_returnsLoggedIn_WhenValidInput() throws loginException {
		
		try {
			user = userDAO.login("ml404", "password");
		} catch (Exception e) {
			// TODO: handle exception
		}
		assertNotNull(user);
		
		assertEquals("Admin", user.getStatus());
	}
	
	@Test
	public void test_updateUser_returnsNull_WhenNullInput() {
		User actual = userDAO.updateUser(null, null, null, null, null,null);
		assertEquals(null, actual);
	}
	
	@Test
	public void test_updateUser_returnsNull_WhenInvalidUsernameInput() {
		entityTransaction.begin();
		User actual = userDAO.updateUser("username", "password", "firstName", "lastName", "email","status");
		assertEquals(null, actual);
	}
	
	@Test
	public void test_updateUser_returnsUpdatedUser_WhenValidInput() {
		entityTransaction.begin();
		userDAO.updateUser("jacksond80", "password", "Darryl", "Jackson", "darryl.jackson@fdmgroup.com","Admin");
		entityTransaction.commit();
		User actual = null;
		try {
			actual = userDAO.findUser("jacksond80");
		} catch (loginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("darryl.jackson@fdmgroup.com", actual.getEmail());
		
	}
	
	
	@Test
	public void test_removeUser_returnsOneWithValidUsername() {
		entityTransaction.begin();
		int actual = userDAO.removeUser("jacksond80");
		assertEquals(1, actual);
		userDAO.changeUserStatus("jacksond80", "Admin");
		entityTransaction.commit();
	}
	
	@Test
	public void test_removeUser_returnsZeroWithInvalidUsername() {
		entityTransaction.begin();
		int actual = userDAO.removeUser("notrealuser");
		assertEquals(0, actual);
		entityTransaction.commit();
	}

}
