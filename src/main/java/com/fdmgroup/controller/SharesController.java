package com.fdmgroup.controller;

import java.sql.Date;
import java.util.List;
import java.util.Random;

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
import com.fdmgroup.model.Broker;
import com.fdmgroup.model.BrokerStockExchange;
import com.fdmgroup.model.Company;
import com.fdmgroup.model.Share;
import com.fdmgroup.model.SharePrice;
import com.fdmgroup.model.StockExchange;
import com.fdmgroup.model.Trade;
import com.fdmgroup.model.User;
import com.fdmgroup.model.Wallet;
import com.fdmgroup.model.DAO.BrokerDAO;
import com.fdmgroup.model.DAO.BrokerStockExchangeDAO;
import com.fdmgroup.model.DAO.ExchangeRateDAO;
import com.fdmgroup.model.DAO.ShareDAO;
import com.fdmgroup.model.DAO.SharePriceDAO;
import com.fdmgroup.model.DAO.TradeDAO;
import com.fdmgroup.model.DAO.UserDAO;
import com.fdmgroup.validation.HoldingException;
import com.fdmgroup.validation.ShareException;
import com.fdmgroup.validation.SharePriceException;

@Controller
public class SharesController {
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;
	static Logger log = Logger.getLogger(SharesController.class);
	private SharePriceDAO sharePriceDAO = new SharePriceDAO();

	public HttpSession setup(HttpServletRequest request) {
		entityManagerFactory = Persistence.createEntityManagerFactory("groupproject");
		entityManager = entityManagerFactory.createEntityManager();
		HttpSession session = request.getSession();
		return session;
	}

	@RequestMapping("/shares")
	public String sharesHandler(Model model, HttpServletRequest request) {
		log.info("Entering SharesPage");
		List<SharePrice> sharePrices = sharePriceDAO.listOfCurrentPrices();
		model.addAttribute("prices", sharePrices);
		model.addAttribute("basket", new Basket());
		return "sharesPage";
	}

	@RequestMapping("/shareInf")
	public String shareInfHandler(Model model, HttpServletRequest request) throws ShareException, SharePriceException {
		if (request.getParameter("share_id") != null) {
			int share_id = Integer.parseInt(request.getParameter("share_id"));
			int selection = 0;
			if (request.getParameter("selection") != null) {
				selection = Integer.parseInt(request.getParameter("selection"));
			}
			log.info("Entering ShareInfoPage with share_id = " + share_id);
			ShareDAO shareDAO = new ShareDAO();
			SharePriceDAO sharePriceDAO = new SharePriceDAO();
			Share share = shareDAO.getShare(share_id);
			Company company = share.getCompany();
			List<SharePrice> sharePrices = sharePriceDAO.list(share_id);
			double currentPrice = sharePriceDAO.currentPrice(share_id);
			List<SharePrice> sharePricesFut = sharePriceDAO.listFuture(share_id);
			List<SharePrice> sharePricesPast = sharePriceDAO.listPast(share_id);
			if (selection == 1) {
				sharePrices = sharePricesFut;
			}
			if (selection == 2) {
				sharePrices = sharePricesPast;
			}
			model.addAttribute("prices", currentPrice);
			model.addAttribute("share", share);
			model.addAttribute("sharePrices", sharePrices);
			model.addAttribute("company", company);
			model.addAttribute("basket", new Basket());
			return "shareInf";
		} else {
			return "sharesPage";
		}
	}

	@RequestMapping("/buy")
	public String buyHandler(Model model, HttpServletRequest request, Basket basket)
			throws ShareException, SharePriceException, HoldingException {
		HttpSession session = setup(request);
		LoginController loginController = new LoginController();
		if (session.getAttribute("userInf") instanceof User) {
			User userInf = (User) session.getAttribute("userInf");
			if (userInf.getUsername().equals("Login")) {
				return loginController.loginHandler(model, request);
			}
			entityTransaction = entityManager.getTransaction();
			// external user -> redirection to login page
			int share_id = Integer.parseInt(request.getParameter("share_id"));
			basket.setShare_id(share_id);
			log.info("Trying to buy share with share_id = " + basket.getShare_id());
			ShareDAO shareDAO = new ShareDAO();
			TradeDAO tradeDAO = new TradeDAO(entityManager);
			SharePriceDAO sharePriceDAO = new SharePriceDAO();
			ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAO(entityManager);
			Share share = shareDAO.getShare(share_id);
			double price = sharePriceDAO.currentPrice(share_id);
			double price_total = 0.0;
			if (share.getCurrency().getCurrency_id() == 4) {
				price_total = price * basket.getQuantity()/100;
			} else {
				price_total = exchangeRateDAO.convert(share.getCurrency().getCurrency_id(), 3, price * basket.getQuantity());				
			}
			Wallet wallet = (Wallet) session.getAttribute("wallet");
			if (wallet.getMoney() >= price_total) {
				UserDAO userDAO = new UserDAO(entityManager);
				wallet.setMoney(wallet.getMoney() - price_total);
				entityTransaction.begin();
				userDAO.setMoney(userInf.getUserId(), wallet.getMoney() - price_total);
				entityTransaction.commit();
				Date date = new Date(System.currentTimeMillis());
				// generates random broker based on whether he works on the stock exchange the shares are being sold on;
				Broker broker = brokerGeneratorHandler(share);
				Trade trade = new Trade(share, broker, userInf, share.getStockExchange(), date, basket.getQuantity(), price_total, "buy");
				entityTransaction.begin();
				tradeDAO.addTrade(trade);
				entityTransaction.commit();
				session.setAttribute("wallet", wallet);
				model.addAttribute("price", price);
				model.addAttribute("trade", trade);
			} else {
				model.addAttribute("trade", null);				
			}
			return loginController.loginHandler(model, request);
		} else {
			User userInf = new User();
			userInf.setUsername("Login");
			session.setAttribute("userInf", userInf);
		}
		return "tradePage";
	}

	@RequestMapping("/sell")
	public String sellHandler(Model model, HttpServletRequest request, Basket basket)
			throws ShareException, SharePriceException, HoldingException {
		HttpSession session = setup(request);
		LoginController loginControlller = new LoginController();
		if (session.getAttribute("userInf") instanceof User) {
			User userInf = (User) session.getAttribute("userInf");
			if (userInf.getUsername().equals("Login")) {
				return loginControlller.loginHandler(model, request);
			}
			entityTransaction = entityManager.getTransaction();
			// external user -> redirection to login page
			int share_id = Integer.parseInt(request.getParameter("share_id"));
			basket.setShare_id(share_id);
			log.info("Trying to sell share with share_id = " + basket.getShare_id());
			ShareDAO shareDAO = new ShareDAO();
			TradeDAO tradeDAO = new TradeDAO(entityManager);
			SharePriceDAO sharePriceDAO = new SharePriceDAO();
			ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAO(entityManager);
			Share share = shareDAO.getShare(share_id);
			double price = sharePriceDAO.currentPrice(share_id);
			double price_total = 0.0;
			if (share.getCurrency().getCurrency_id() == 4) {
				price_total = price * basket.getQuantity()/100;
			} else {
				price_total = exchangeRateDAO.convert(share.getCurrency().getCurrency_id(), 3, price * basket.getQuantity());				
			}
			Wallet wallet = (Wallet) session.getAttribute("wallet");
			Trade trade;
			try {
				Date date = new Date(System.currentTimeMillis());
				// generates random broker based on whether he works on the stock exchange the shares are being sold on;
				Broker broker = brokerGeneratorHandler(share);
				trade = new Trade(share, broker, userInf, share.getStockExchange(), date, basket.getQuantity(), price_total, "sell");
				entityTransaction.begin();
				tradeDAO.addTrade(trade);
				entityTransaction.commit();
				wallet.setMoney(wallet.getMoney() + price_total);
			} catch(HoldingException e) {
				trade = null;
			}
			model.addAttribute("price", price);
			model.addAttribute("trade", trade);
			return loginControlller.loginHandler(model, request);
		} else {
			User userInf = new User();
			userInf.setUsername("Login");
			session.setAttribute("userInf", userInf);
		}
		return "tradePage";
	}

	public Broker brokerGeneratorHandler(Share share) {
		log.info("Calling available brokers");
		Random randomGenerator = new Random();
		BrokerStockExchangeDAO brokerExchangeDAO = new BrokerStockExchangeDAO(entityManager);
		List<Broker> listBrokers = brokerExchangeDAO.listBrokersForStockExchange(share.getStockExchange().getStock_ex_id());
		if (listBrokers.size() == 0) {
			log.info("No available brokers, assigning Broker");
			BrokerDAO brokerDAO = new BrokerDAO(entityManager);
			listBrokers = brokerDAO.listBrokers();
			int index = randomGenerator.nextInt(listBrokers.size());
			Broker broker = listBrokers.get(index);
			log.info("Assigning Broker to Stock Exchange");	
			int broker_id = broker.getBroker_id();
			int stock_ex_id = share.getStockExchange().getStock_ex_id();
			BrokerStockExchange newBrokerStockExchange = new BrokerStockExchange(entityManager.find(Broker.class, broker_id),entityManager.find(StockExchange.class, stock_ex_id));
			entityTransaction.begin();
			brokerExchangeDAO.addBrokerStockExchange(newBrokerStockExchange);
			entityTransaction.commit();
			return broker;
		}
		int index = randomGenerator.nextInt(listBrokers.size());
		Broker broker = listBrokers.get(index);
		return broker;
	}
}