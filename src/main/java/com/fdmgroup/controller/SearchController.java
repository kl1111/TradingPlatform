package com.fdmgroup.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.fdmgroup.model.Basket;
import com.fdmgroup.model.SharePrice;
import com.fdmgroup.model.DAO.SharePriceDAO;

@Controller
public class SearchController {

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private SharePriceDAO sharePriceDAO;

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

	public void close() {
		entityManager.close();
		entityManagerFactory.close();
	}

	@RequestMapping(value = "/submitSearch", method = GET)
	public String submitSearch(Model model, @RequestParam("searchValue") String searchValue, HttpServletRequest request)
			throws Exception {
		@SuppressWarnings("unused")
		HttpSession session = setup(request);
		sharePriceDAO = new SharePriceDAO();
		entityTransaction = entityManager.getTransaction();
		SharePrice[] result = sharePriceDAO.findLatestSharePriceByCompanyNameCurrencyNameOrStockExName(searchValue);
		model.addAttribute("prices", result);
		close();
		log.info("User searched for Shares, returning results");
		model.addAttribute("basket",new Basket());
		return "sharesPage";

	}

}