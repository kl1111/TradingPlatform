package com.fdmgroup.controller;

import java.util.List;

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
import com.fdmgroup.model.Holding;
import com.fdmgroup.model.PasswordUpdate;
import com.fdmgroup.model.SharePrice;
import com.fdmgroup.model.Trade;
import com.fdmgroup.model.User;
import com.fdmgroup.model.DAO.HoldingDAO;
import com.fdmgroup.model.DAO.SharePriceDAO;
import com.fdmgroup.model.DAO.TradeDAO;
import com.fdmgroup.model.DAO.UserDAO;
import com.fdmgroup.validation.HoldingException;

@Controller
public class AccountController {
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;
	private SharePriceDAO sharePriceDAO = new SharePriceDAO();
	static Logger log = Logger.getLogger(AccountController.class);
	
	public HttpSession setup(HttpServletRequest request) {
		entityManagerFactory = Persistence.createEntityManagerFactory("groupproject");
		entityManager = entityManagerFactory.createEntityManager();
		HttpSession session = request.getSession();
		return session;
	}
	
	@RequestMapping("/account")
	public String accountHandler(Model model, HttpServletRequest request) {
		HttpSession session = setup(request);
		User user = (User) session.getAttribute("userInf");
		TradeDAO tradeDAO = new TradeDAO(entityManager);
		HoldingDAO holdingDAO = new HoldingDAO(entityManager);
		List<Trade> trades = tradeDAO.listTradesByUserId(user.getUserId());
		List<Holding> holdings = null;
		try {
			holdings = holdingDAO.listOfAllCurrentHoldingsByUser(user);
		} catch (HoldingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("trades",trades);
		model.addAttribute("holdings",holdings);
		List<SharePrice> sharePrices = sharePriceDAO.listOfCurrentPrices();
		model.addAttribute("prices",sharePrices);
		model.addAttribute("update", new PasswordUpdate());
		model.addAttribute("basket", new Basket());
		return "accountPage";
	}
	
	@RequestMapping("/passwordupdate")
	public String passwordUpdate(Model model, HttpServletRequest request, PasswordUpdate password){
		HttpSession session = setup(request);
		entityManagerFactory = Persistence.createEntityManagerFactory("groupproject");
		entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		User userInf = (User) session.getAttribute("userInf");
		User user = null;
		if (userInf.getPassword().equals(password.getPasswordold())) {
			if(password.getPasswordnew().equals(password.getConfirm())){
			UserDAO userDAO = new UserDAO(entityManager);
			user = entityManager.find(User.class, userInf.getUserId());	
			entityTransaction.begin();
			userDAO.updateUser(user.getUsername(), password.getPasswordnew(), user.getFirstname(), user.getLastname(), user.getEmail(), user.getStatus());
			entityTransaction.commit();
			user.setPassword(password.getPasswordnew());
			session.setAttribute("userInf", user);
			model.addAttribute("updatep", "Success!");
			}
			else {
				model.addAttribute("updatep", "The two entered passwords didn't match, please try again");
			}
		} else {
			model.addAttribute("updatep", "Failed: Incorrect password");
		}
		LoginController loginController = new LoginController();
		return loginController.loginHandler(model, request);
	}
	
	@RequestMapping("/deleteAccount")
	public String accountDeleteSubmit(Model model, HttpServletRequest request) {
		HttpSession session = setup(request);
		User user = (User) session.getAttribute("userInf");
		UserDAO userDAO = new UserDAO(entityManager);
		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		userDAO.removeUser(user.getUsername());
		entityTransaction.commit();
		LoginController loginController = new LoginController();
		return loginController.logoutHandler(model, request);
	}
	@RequestMapping("/tradeHistory")
	public String tradeHistory(Model model, HttpServletRequest request) {
		HttpSession session = setup(request);
		User user = (User) session.getAttribute("userInf");
		TradeDAO tradeDAO = new TradeDAO(entityManager);
		List<Trade> trades = tradeDAO.listTradesByUserId(user.getUserId());
		model.addAttribute("trades",trades);
		List<SharePrice> sharePrices = sharePriceDAO.listOfCurrentPrices();
		model.addAttribute("prices",sharePrices);
		model.addAttribute("update", new PasswordUpdate());
		return "tradeHistory";
	}
	
	
	
}
