package com.fdmgroup.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fdmgroup.model.Basket;
import com.fdmgroup.model.User;
import com.fdmgroup.model.Wallet;
import com.fdmgroup.model.DAO.UserDAO;
import com.fdmgroup.validation.loginException;

@Controller
public class LoginController {

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	

	@SuppressWarnings("unused")
	private EntityTransaction entityTransaction;
	static Logger log = Logger.getLogger(LoginController.class);
	// ALL requests for "/" sent to login.jsp
	

	public HttpSession setup(HttpServletRequest request) {
		entityManagerFactory = Persistence.createEntityManagerFactory("groupproject");
		entityManager = entityManagerFactory.createEntityManager();
		HttpSession session = request.getSession();
		return session;
	}
	
	@RequestMapping("/login")
	public String loginHandler(Model model, HttpServletRequest request) {
		HttpSession session = setup(request);
		if(session.getAttribute("userInf") instanceof User){
		User userInf = (User) session.getAttribute("userInf");
		if(!userInf.getUsername().equals("Login")){
			AccountController accountController = new AccountController();
			return accountController.accountHandler(model, request);
		}
		}
		else{
			User userInf = new User();
			userInf.setUsername("Login");
			session.setAttribute("userInf", userInf);
		}
		
		log.info("Entering LoginPage");
		model.addAttribute("user", new User());
		model.addAttribute("basket", new Basket());
		return "loginPage";
	}
	@RequestMapping("/logout")
	public String logoutHandler(Model model, HttpServletRequest request) {
		HttpSession session = setup(request);
		session.invalidate();
		HomeController homeController = new HomeController();
		return homeController.HomepageHandler(model, request);	
	}

	@RequestMapping("/loginSubmit")
	public String loginSubmitHandler(Model model, User user, HttpServletRequest request) throws loginException {
		HttpSession session = setup(request);
		entityTransaction = entityManager.getTransaction();
		log.info("Submitting LoginInfo");
		String username = user.getUsername();
		String password = user.getPassword();
		UserDAO userDAO = new UserDAO(entityManager);
		User result = null;
		String error=null;
		try {
			result = userDAO.login(username, password);
		} catch (loginException e) {
			error = e.getMessage();
		}
		if(result==null){
			model.addAttribute("error", error);
			return "loginPage";
		}
		if(userDAO.findUsername(user.getUsername()).getStatus().equals("Deleted"))
			return "homePage";
		else{
			Wallet wallet = entityManager.find(Wallet.class, result.getUserId());
		session.setAttribute("userInf", result);
		session.setAttribute("wallet", wallet);
		return "homePage";
		}
	}
}