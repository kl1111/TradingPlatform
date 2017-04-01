package com.fdmgroup.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;

import com.fdmgroup.model.User;
import com.fdmgroup.validation.loginException;

public class LoginControllerTest {

	private LoginController loginController = new LoginController();
	private HttpServletRequest request = mock(HttpServletRequest.class);
	private HttpSession session = mock(HttpSession.class);
	private Model model = mock(Model.class);
	private User user = mock(User.class);
	private User userExistsInDatabase = new User("ml404", "password", "Matt", "Layton", "ml404@kent.ac.uk", "Admin");

	@Before
	public void before() {
		when(request.getSession()).thenReturn(session);
	}


	@Test
	public void test_setup_returns_session_whenCalled() {
		HttpSession actual = loginController.setup(request);
		assertEquals(session, actual);
	}

	@Test
	public void test_LoginHandler_Returns_account_withUserLoggedIn() {
		userExistsInDatabase.setUserId(2);
		when(session.getAttribute("userInf")).thenReturn(userExistsInDatabase);
		when(session.getAttribute("user")).thenReturn(userExistsInDatabase);
		String actual = loginController.loginHandler(model, request);
		assertEquals("accountPage", actual);
	}

	@Test
	public void test_LoginHandler_Returns_loginPage_withNoUserLoggedIn() {
		when(session.getAttribute("userInf")).thenReturn(user);
		when(user.getUsername()).thenReturn("Login");
		String actual = loginController.loginHandler(model, request);
		assertEquals("loginPage", actual);
	}

	@Test
	public void test_LoginSubmitHandler_Returns_loginPage_withNonDatabaseUser() throws loginException {
		String actual = loginController.loginSubmitHandler(model, user, request);
		assertEquals("loginPage", actual);
	}

	@Test
	public void test_LoginSubmitHandler_Returns_Account_withDatabaseUser() throws loginException {
		String actual = loginController.loginSubmitHandler(model, userExistsInDatabase, request);
		assertEquals("homePage", actual);
	}

	@Test
	public void test_LogoutHandler_ReturnshomePage() {
		String actual = loginController.logoutHandler(model, request);
		assertEquals("homePage", actual);
	}

}
