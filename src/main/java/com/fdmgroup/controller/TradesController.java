package com.fdmgroup.controller;

import java.sql.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fdmgroup.model.Broker;
import com.fdmgroup.model.Share;
import com.fdmgroup.model.StockExchange;
import com.fdmgroup.model.Trade;
import com.fdmgroup.model.User;
import com.fdmgroup.model.DAO.ShareDAO;
import com.fdmgroup.model.DAO.SharePriceDAO;
import com.fdmgroup.model.DAO.TradeDAO;
import com.fdmgroup.validation.HoldingException;
import com.fdmgroup.validation.ShareException;
import com.fdmgroup.validation.SharePriceException;

public class TradesController {

	final static Logger log = Logger.getLogger(TradesController.class);
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;
	private List<Trade> listOfTrades;
	private TradeDAO tradeDAO;
	private ShareDAO shareDAO; 
	private SharePriceDAO sharePriceDAO;
	private double sharePrice;
	@SuppressWarnings("deprecation")
	private Date date = new Date(2017, 1, 10); //(Date) Calendar.getInstance().getTime();
	private Share share;
	@SuppressWarnings("unused")
	private double pricetotal;
	
	public HttpSession setup(HttpServletRequest request) {
		entityManagerFactory = Persistence.createEntityManagerFactory("groupproject");
		entityManager = entityManagerFactory.createEntityManager();
		HttpSession session = request.getSession();
		entityTransaction = entityManager.getTransaction();
		return session;
	}

	@RequestMapping(value = "/listTrades")
	public String listTrades(Model model, HttpServletRequest request) {
		setup(request);
		int selection = 0;
		if (request.getParameter("selection") != null) {
			selection = Integer.parseInt(request.getParameter("selection"));
		}
		String message = "No Input";
		if (request.getParameter("param_id") != null) {
			tradeDAO = new TradeDAO(entityManager);
			int param_id = Integer.parseInt(request.getParameter("param_id"));
			setup(request);
			switch (selection) {
			case 1:
				listOfTrades = tradeDAO.listTradesByShareId(param_id);
				message = "Listing Trades by Shares";
				break;
			case 2:
				listOfTrades = tradeDAO.listTradesByBrokerId(param_id);
				message = "Listing Trades by Brokers";
				break;
			case 3:
				listOfTrades = tradeDAO.listTradesByUserId(param_id);
				message = "Listing Trades by Users";
				break;
			case 4:
				listOfTrades = tradeDAO.listTradesByStockExId(param_id);
				message = "Listing Trades by stock_ex_id";
				break;
			case 5:
				listOfTrades = tradeDAO.listTradesByPriceTotal(param_id);
				message = "Listing Trades by priceTotal";
				break;
			case 6:
				listOfTrades = tradeDAO.listTradesByYear(param_id);
				message = "Listing Trades by Year";
				break;
			case 7:
				listOfTrades = tradeDAO.listTradesByMonth(tradeDAO.monthFinder(param_id));
				message = "Listing Trades by Month";
				break;
			case 8:
				listOfTrades = tradeDAO.listTradesByLast2Weeks();
				message = "Listing Trades by last 2 Weeks";
				break;
			case 9:
				listOfTrades = tradeDAO.listTradesByLastWeek();
				message = "Listing Trades in last Week";
				break;
			case 10:
				listOfTrades = tradeDAO.listTradesByToday();
				message = "Listing Trades made today";
				break;
			case 11:
				Trade trade = tradeDAO.findTrade(param_id);
				model.addAttribute("trade", trade);
				message = "Found Trade";
				break;
			default:
				message = "No Selection";
				break;
			}
			close();
			if (listOfTrades != null) {
				model.addAttribute("listOfTrades", listOfTrades);
			}
		}
		log.info(message);
		model.addAttribute("message", message);
		return "accountPage";
	}
	

	public void close() {
		entityManager.close();
		entityManagerFactory.close();
	}

}
