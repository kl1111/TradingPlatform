package com.fdmgroup.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.Model;

import com.fdmgroup.model.User;
import com.fdmgroup.model.DAO.UserDAO;
import com.fdmgroup.validation.loginException;

public class RegistrationControllerTest {
	private RegistrationController registrationController = new RegistrationController();
	private HttpServletRequest request = mock(HttpServletRequest.class);
	private HttpSession session = mock(HttpSession.class);
	private Model model = mock(Model.class);
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;
	private User user = new User();
	private User userExistsInDatabase = new User("ml404", "password", "Matt", "Layton", "ml404@kent.ac.uk", "Admin");
	
	@Before
	public void Before(){
		entityManagerFactory = Persistence.createEntityManagerFactory("groupproject");
		entityManager = entityManagerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction();
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("userInf")).thenReturn(user);
	}
	
	@Test
	public void test_setup_returns_session_whenCalled(){
		HttpSession actual=registrationController.setup(request);
		assertEquals(session,actual);
	}
	
	@Test
	public void test_registerHandler_returnsRegistrationPage_ifNotLoggedIn(){
		user.setUsername("Login");
		String actual = registrationController.registerHandler(model, request);
		assertEquals("registrationPage", actual);
	}
	
	@Test
	public void test_registerHandler_returnsAccountPage_ifLoggedIn(){
		user.setUsername("Steve");
		userExistsInDatabase.setUserId(2);
		when(session.getAttribute("userInf")).thenReturn(userExistsInDatabase);	
		String actual = registrationController.registerHandler(model, request);
		assertEquals("accountPage", actual);
	}
	
	@Test
	public void test_registerHandler_attachesDefaultUserToSession_ifNoneAttached(){
		when(session.getAttribute("userInf")).thenReturn(null);
		registrationController.registerHandler(model, request);
		ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);	
		verify(session).setAttribute(anyString(), argument.capture());
		assertEquals("Login",argument.getValue().getUsername());
	}
	
	
	@Test
	public void test_registerSubmitHandler_returnsUserAlreadyRegisteredError_ifUsernameAlreadyExists(){
		user = new User("ml404","password","a","a","a","a");
		registrationController.registerSubmitHandler(model, user, request);
		verify(model).addAttribute("register","Username is already in use");
	}
	
	@Test
	public void test_registerSubmitHandler_returnsUserToRegistrationPage_ifUsernameAlreadyExists(){
		user = new User("ml404","password","a","a","a","a");
		String actual = registrationController.registerSubmitHandler(model, user, request);
		verify(model).addAttribute("register","Username is already in use");
		assertEquals(actual,"registrationPage");
	}
	
	@Test
	public void test_registerSubmitHandler_attachesCorrectUser_ifValidRegistration(){
		user = new User("tester","password","a","a","a", "a");
		ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);		
		entityTransaction.begin();
		UserDAO userDAO = new UserDAO(entityManager);
		try {
		userDAO.deleteUser("tester");
		} catch (loginException e) {
			// TODO Auto-generated catch block
		}
		entityTransaction.commit();
		registrationController.registerSubmitHandler(model, user, request);
		verify(session,times(1)).setAttribute(anyString(),argument.capture());
		assertEquals(user.getUsername(),argument.getValue().getUsername());
		entityTransaction.begin();
		try {
		userDAO.deleteUser("tester");
		} catch (loginException e) {
			// TODO Auto-generated catch block
		}
		entityTransaction.commit();
	}
	
	@Test
	public void test_registerSubmitHandler_navigatesToHomePage_ifValidRegistration(){
		user = new User("tester","password","a","a","a", "a");
		entityTransaction.begin();
		UserDAO userDAO = new UserDAO(entityManager);
		try {
		userDAO.deleteUser("tester");
		} catch (loginException e) {
			// TODO Auto-generated catch block
		}
		entityTransaction.commit();
		String actual = registrationController.registerSubmitHandler(model, user, request);
		assertEquals(actual, "homePage");
		
		entityTransaction.begin();
		try {
		userDAO.deleteUser("tester");
		} catch (loginException e) {
			// TODO Auto-generated catch block
		}
		entityTransaction.commit();
	}
}
