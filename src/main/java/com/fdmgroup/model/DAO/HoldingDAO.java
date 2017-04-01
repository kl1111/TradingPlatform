package com.fdmgroup.model.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.fdmgroup.model.Holding;
import com.fdmgroup.model.HoldingConcatenation;
import com.fdmgroup.model.Share;
import com.fdmgroup.model.SharePrice;
import com.fdmgroup.model.User;
import com.fdmgroup.validation.HoldingException;

public class HoldingDAO {
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;
	static Logger log = Logger.getLogger(HoldingDAO.class);
	
	public HoldingDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
		entityTransaction = entityManager.getTransaction();
	}

	public Holding editHoldings(User user, Share share, int quantity, int sell) throws HoldingException{
		if (user == null)
			throw new HoldingException("Insert holding: User not found.");
		if (share == null)
			throw new HoldingException("Insert holding: Share not found.");
		if (quantity == 0)
			throw new HoldingException("Insert holding: No quantity.");
		Holding holding = new Holding(user, share, quantity, sell);
		HoldingConcatenation holdingConcatenation = new HoldingConcatenation(holding.getUser_id().getUserId(),holding.getShare_id().getShare_id());
		if(entityManager.find(Holding.class, holdingConcatenation) != null){
			Holding oldHolding = entityManager.find(Holding.class, holdingConcatenation);
			if(oldHolding.getQuantity()+quantity < 0){
				throw new HoldingException("Insert holding: Invalid quantity.");
			}
			holding.setQuantity(oldHolding.getQuantity()+quantity);
		}
		entityManager.merge(holding);
		log.info("Inserted a new holding.");
		return holding;
	}
	
	public List<Holding> listOfAllCurrentHoldingsByUser(User user) throws HoldingException{
		if (user == null)
			throw new HoldingException("Insert holding: User not found.");
		Query query1 = entityManager
				.createNativeQuery("SELECT user_id,share_id,quantity,sell FROM holdings WHERE user_id =" + user.getUserId(), Holding.class);
		List<Holding> results = query1.getResultList();
		return results;
	}
	
}
