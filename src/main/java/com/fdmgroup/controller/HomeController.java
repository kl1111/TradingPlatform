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

@Controller
public class HomeController {

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;


	@SuppressWarnings("unused")
	private EntityTransaction entityTransaction;
	static Logger log = Logger.getLogger(HomeController.class);
	// ALL requests for "/" sent to login.jsp

	public HttpSession setup(HttpServletRequest request) {
		entityManagerFactory = Persistence.createEntityManagerFactory("groupproject");
		entityManager = entityManagerFactory.createEntityManager();
		HttpSession session = request.getSession();
		return session;
	}
	
	public void close(){
		entityManager.close();
		entityManagerFactory.close();
	}

	@RequestMapping("/")
	public String HomepageHandler(Model model, HttpServletRequest request) {
		log.info("Entering Homepage");
		HttpSession session = setup(request);
		model.addAttribute("user", new User());
		if(session.getAttribute("userInf")==null){
			User userInf = new User();
			userInf.setUsername("Login");
			session.setAttribute("userInf", userInf);
		}
		close();
		return "homePage";
	}
	@RequestMapping("/home")
	public String homeRouter(Model model, HttpServletRequest request) {
		return HomepageHandler(model,request);
	}

}