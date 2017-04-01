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

import com.fdmgroup.model.Company;
import com.fdmgroup.model.Share;
import com.fdmgroup.model.SharePrice;
import com.fdmgroup.model.StockExchange;
import com.fdmgroup.model.DAO.CompanyDAO;
import com.fdmgroup.model.DAO.ShareDAO;
import com.fdmgroup.model.DAO.SharePriceDAO;
import com.fdmgroup.model.DAO.StockExchangeDAO;
import com.fdmgroup.validation.ShareException;

@Controller
public class StockExchangeController {
	
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	@SuppressWarnings("unused")
	private EntityTransaction entityTransaction;
	static Logger log = Logger.getLogger(SharesController.class);
	public HttpSession setup(HttpServletRequest request) {
		entityManagerFactory = Persistence.createEntityManagerFactory("groupproject");
		entityManager = entityManagerFactory.createEntityManager();
		HttpSession session = request.getSession();
		return session;
	}	
	

		
		@RequestMapping("/stockInfo")
		public String stockInfoHandler(Model model, HttpServletRequest request) throws ShareException{
			@SuppressWarnings("unused")
			HttpSession session = setup(request);
			if (request.getParameter("share_id") != null) {
				int share_id = Integer.parseInt(request.getParameter("share_id"));
				log.info("Entering StockExchangeInfo page with share_id = " + share_id);
				ShareDAO shareDAO = new ShareDAO();
				Share share = shareDAO.getShare(share_id);
				StockExchangeDAO stockExchangeDAO = new StockExchangeDAO(entityManager);
				StockExchange stockExchange = stockExchangeDAO.findStockExchange(share.getStockExchange().getStock_ex_id());
				CompanyDAO companyDAO = new CompanyDAO(entityManager);
				Company[] companyList = companyDAO.listCompanies();
				SharePriceDAO sharePriceDAO = new SharePriceDAO();
				List<SharePrice> result = sharePriceDAO.list(share.getShare_id());
			
				model.addAttribute("sharePrice", result);
				model.addAttribute("share", share);
				model.addAttribute("stockExchange", stockExchange);
				model.addAttribute("companyList",companyList);
				return "stock_exchange";
			} else {
				return "sharesPage";
			}
	
	}
}


