package com.fdmgroup.model.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.fdmgroup.model.Trade;
import com.fdmgroup.validation.HoldingException;

public class TradeDAO {

	private EntityManager entityManager;
	private List<Trade> trades;
	EntityManagerFactory entityManagerFactory;
	EntityTransaction entityTransaction;
	

	public TradeDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
		entityTransaction = entityManager.getTransaction();
	}

	public Trade addTrade(Trade trade) throws HoldingException {
		if (trade == null)
			return null;
		entityManager.merge(trade);
		HoldingDAO holdingDAO = new HoldingDAO(entityManager);
		if (trade.getAction().equals("buy"))
			holdingDAO.editHoldings(trade.getUser(), trade.getShare(), trade.getShare_amount(), 0);
		else
			holdingDAO.editHoldings(trade.getUser(), trade.getShare(), -trade.getShare_amount(), 0);
		return trade;
	}

	@SuppressWarnings("unchecked")
	public List<Trade> listTradesByShareId(int id) {
		if (id == 0)
			return null;
		trades = entityManager.createNativeQuery(
				"SELECT trade_id, share_id, broker_id, stock_ex_id, transaction_time, share_amount, price_total, action FROM trades WHERE share_id="
						+ id,
				Trade.class).getResultList();
		return trades;
	}

	@SuppressWarnings("unchecked")
	public List<Trade> listTradesByTwoParameters(String listOne, String listTwo, int listOneId, int listTwoId) {
		if (listOneId == 0 || listTwoId == 0)
			return null;
		if (listOne.equals(null) || listTwo.equals(null))
			return null;
		try {
			trades = entityManager
					.createNativeQuery(
							"SELECT trade_id, share_id, broker_id, stock_ex_id, transaction_time, share_amount, price_total, action FROM trades WHERE "
									+ listOne + "=" + listOneId + " AND " + listTwo + "=" + listTwoId,
							Trade.class)
					.getResultList();
		} catch (Exception e) {
			return null;
		}
		return trades;

	}

	@SuppressWarnings("unchecked")
	public List<Trade> listTradesByBrokerId(int id) {
		if (id == 0)
			return null;
		trades = entityManager.createNativeQuery(
				"SELECT trade_id, share_id, broker_id, stock_ex_id, transaction_time, share_amount, price_total, action FROM trades WHERE broker_id="
						+ id,
				Trade.class).getResultList();
		return trades;
	}
	
	@SuppressWarnings("unchecked")
	public List<Trade> listTradesByUserId(int id) {
		if (id == 0)
			return null;
		trades = entityManager.createNativeQuery(
				"SELECT trade_id, share_id, broker_id, stock_ex_id, transaction_time, share_amount, price_total, action FROM trades WHERE user_id="
						+ id + " ORDER BY trade_id",
				Trade.class).getResultList();
		return trades;
	}

	@SuppressWarnings("unchecked")
	public List<Trade> listTradesByStockExId(int id) {
		if (id == 0)
			return null;
		trades = entityManager.createNativeQuery(
				"SELECT trade_id, share_id, broker_id, stock_ex_id, transaction_time, share_amount, price_total, action FROM trades WHERE stock_ex_id="
						+ id,
				Trade.class).getResultList();
		return trades;
	}

	@SuppressWarnings("unchecked")
	public List<Trade> listTradesByShareAmount(int i) {
		if (i == 0)
			return null;
		trades = entityManager.createNativeQuery(
				"SELECT trade_id, share_id, broker_id, stock_ex_id, transaction_time, share_amount, price_total, action FROM trades WHERE share_amount="
						+ i,
				Trade.class).getResultList();
		return trades;
	}

	@SuppressWarnings("unchecked")
	public List<Trade> listTradesByShareAmount(int i, String symbol) {
		if (i == 0)
			return null;
		if (symbol == null || symbol.isEmpty())
			return listTradesByShareAmount(i);
		trades = entityManager.createNativeQuery(
				"SELECT trade_id, share_id, broker_id, stock_ex_id, transaction_time, share_amount, price_total, action FROM trades WHERE share_amount"
						+ symbol + "=" + i,
				Trade.class).getResultList();
		return trades;
	}

	@SuppressWarnings("unchecked")
	public List<Trade> listTradesByPriceTotal(double priceTotal) {
		if (priceTotal == 0)
			return null;
		trades = entityManager.createNativeQuery(
				"SELECT trade_id, share_id, broker_id, stock_ex_id, transaction_time, share_amount, price_total, action FROM trades WHERE price_total="
						+ priceTotal,
				Trade.class).getResultList();
		return trades;
	}

	@SuppressWarnings("unchecked")
	public List<Trade> listTradesByPriceTotal(double priceTotal, String symbol) {
		if (priceTotal == 0)
			return null;
		if (symbol == null)
			return listTradesByPriceTotal(priceTotal);
		trades = entityManager.createNativeQuery(
				"SELECT trade_id, share_id, broker_id, stock_ex_id, transaction_time, share_amount, price_total, action FROM trades WHERE price_total"
						+ symbol + "=" + priceTotal,
				Trade.class).getResultList();
		return trades;
	}

	@SuppressWarnings("unchecked")
	public List<Trade> listTradesByYear(int i) {
		if (i == 0)
			return null;
		trades = entityManager.createNativeQuery(
				"SELECT trade_id, share_id, broker_id, stock_ex_id, transaction_time, share_amount, price_total, action FROM trades WHERE TO_CHAR(transaction_time, 'YYYY') ="
						+ "'" + i + "'",
				Trade.class).getResultList();
		return trades;
	}

	@SuppressWarnings("unchecked")
	public List<Trade> listTradesByMonth(String month) {
		if (month == null)
			return null;
		trades = entityManager.createNativeQuery(
				"SELECT trade_id, share_id, broker_id, stock_ex_id, transaction_time, share_amount, price_total, action FROM trades WHERE TO_CHAR(transaction_time, 'Mon') ="
						+ "'" + month + "'",
				Trade.class).getResultList();
		return trades;
	}

	@SuppressWarnings("unchecked")
	public List<Trade> listTradesByLast2Weeks() {
		trades = entityManager.createNativeQuery(
				"SELECT trade_id, share_id, broker_id, stock_ex_id, transaction_time, share_amount, price_total, action FROM trades WHERE TO_CHAR(transaction_time, 'YYYY-MM-DD')>= TO_CHAR(SYSDATE -14,'YYYY-MM-DD')",
				Trade.class).getResultList();
		return trades;
	}

	@SuppressWarnings("unchecked")
	public List<Trade> listTradesByLastWeek() {
		trades = entityManager.createNativeQuery(
				"SELECT trade_id, share_id, broker_id, stock_ex_id, transaction_time, share_amount, price_total, action FROM trades WHERE TO_CHAR(transaction_time, 'YYYY-MM-DD')>= TO_CHAR(SYSDATE -7,'YYYY-MM-DD')",
				Trade.class).getResultList();
		return trades;
	}

	@SuppressWarnings("unchecked")
	public List<Trade> listTradesByToday() {
		trades = entityManager.createNativeQuery(
				"SELECT trade_id, share_id, broker_id, stock_ex_id, transaction_time, share_amount, price_total, action FROM trades WHERE TO_CHAR(transaction_time, 'YYYY-MM-DD')>= TO_CHAR(SYSDATE,'YYYY-MM-DD')",
				Trade.class).getResultList();
		return trades;
	}

	public void removeTrade(int trade_id) {
		Query query1 = entityManager.createNativeQuery("DELETE FROM trades WHERE trade_id = " + trade_id);
		query1.executeUpdate();
	}

	public Trade findTrade(int trade_id) {
		if (trade_id == 0)
			return null;
		Trade foundTrade = entityManager.find(Trade.class, trade_id);
		if (foundTrade != null) {
			return foundTrade;
		}
		return null;
	}
	
	public String monthFinder(int month){
		String monthString;
		switch (month) {
		case 1:
			monthString = "January";
			break;
		case 2:
			monthString = "February";
			break;
		case 3:
			monthString = "March";
			break;
		case 4:
			monthString = "April";
			break;
		case 5:
			monthString = "May";
			break;
		case 6:
			monthString = "June";
			break;
		case 7:
			monthString = "July";
			break;
		case 8:
			monthString = "August";
			break;
		case 9:
			monthString = "September";
			break;
		case 10:
			monthString = "October";
			break;
		case 11:
			monthString = "November";
			break;
		case 12:
			monthString = "December";
			break;
		default:
			monthString = "Invalid month";
			break;
		}
		return monthString;
	}
	
//	public List<Trade> listTradesOfShareUserOwns(int id) {
//		if (id == 0)
//			return null; 
//		userDAO = new UserDAO(entityManager);
//		User user = userDAO.findUserID(id);
//		if (user == null)
//			return null;
//		List<Trade> allTrades = listTradesByUserId(id);
//		List<Timestamp> dateOfMostRecentTradeForEachShare = entityManager.createNativeQuery("SELECT MAX(transaction_time) FROM trades GROUP BY share_id").getResultList();
//		List<Integer> idOfSharesOwned = new ArrayList<Integer>();
//		for (Trade trade: allTrades) {
//			for (Timestamp time : dateOfMostRecentTradeForEachShare) {
//				long tradeTime = trade.getTransaction_time().getTime();
//				long timeLong = time.getTime();
//				if (tradeTime == timeLong) {
//					idOfSharesOwned.add(trade.getShare_id().getId());
//				}
//			}
//		}
//		List<Trade> result = new ArrayList<Trade>();
//		for (Integer shareId : idOfSharesOwned) {
//			result.addAll(entityManager.createNativeQuery("SELECT trade_id, share_id, broker_id, stock_ex_id, transaction_time, share_amount, price_total FROM trades WHERE broker_id=" + id + " AND share_id=" + shareId, Trade.class).getResultList());
//		}
//		return result;
//	}

}
