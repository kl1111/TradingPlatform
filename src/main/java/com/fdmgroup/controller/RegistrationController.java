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

import com.fdmgroup.model.User;
import com.fdmgroup.model.DAO.UserDAO;
import com.fdmgroup.validation.RegistrationException;

@Controller
public class RegistrationController {

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	@SuppressWarnings("unused")
	private EntityTransaction entityTransaction;
	static Logger log = Logger.getLogger(RegistrationController.class);
	// ALL requests for "/" sent to login.jsp
	

	public HttpSession setup(HttpServletRequest request) {
		entityManagerFactory = Persistence.createEntityManagerFactory("groupproject");
		entityManager = entityManagerFactory.createEntityManager();
		HttpSession session = request.getSession();
		return session;
	}
	
	@RequestMapping("/register")
	public String registerHandler(Model model, HttpServletRequest request) {
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
		log.info("Entering RegistrationPage");
		model.addAttribute("user", new User());
		return "registrationPage";


	}

	@RequestMapping("/registerSubmit")
	public String registerSubmitHandler(Model model, User user, HttpServletRequest request) {
		HttpSession session = setup(request);
		log.info("Submitting RegistrationInfo");
		UserDAO userDAO = new UserDAO(entityManager);
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		User result = null;
		String error=null;
		try {
			result = userDAO.createUser(user.getUsername(), user.getPassword(), user.getFirstname(), user.getLastname(), user.getEmail(), "Regular User");
		} catch (RegistrationException e) {
			error = e.getMessage();
		} 
		entityTransaction.commit();
		if(result==null){
			model.addAttribute("register",error);
			model.addAttribute("user", new User());
			return "registrationPage";
		}
		else{
			session.setAttribute("userInf",result);
			HomeController homeController = new HomeController();
			return homeController.HomepageHandler(model, request);	
		}
	}
}