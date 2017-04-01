package com.fdmgroup.model.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.fdmgroup.model.User;
import com.fdmgroup.model.Wallet;
import com.fdmgroup.validation.RegistrationException;
import com.fdmgroup.validation.loginException;

public class UserDAO {

	private EntityManager entityManager;

	public UserDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public User createUser(String username, String password, String firstName, String lastName, String email,
			String status) throws RegistrationException {
		User user = null;
		if (username == null || password == null || firstName == null || lastName == null || email == null)
			return user;

		try {
			user = findUser(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (user != null) {
			throw new RegistrationException("Username is already in use");
		} else {
			user = new User(username, password, firstName, lastName, email, status);
			entityManager.persist(user);
			Wallet wallet = new Wallet(user.getUserId(), 1000000);
			entityManager.persist(wallet);
			return user;
		}

	}

	public int deleteUser(String username) throws loginException {
		int output = -1;
		User user = null;
		try {
			user = findUser(username);
		} catch (Exception e) {
			throw new loginException("Username does not exist");
		}
		if (user != null) {
			entityManager.remove(user);
			output = 1;
			return output;
		}
		return output;
	}

	public int removeUser(String username) {
		if (username == null) {
			return 0;
		}
		Query query = entityManager
				.createNativeQuery("UPDATE Users SET status= 'Deleted' WHERE username=" + "'" + username + "'");
		int result = query.executeUpdate();
		return result;
	}

	public int changeUserStatus(String username, String status) {
		if (username == null) {
			return 0;
		}
		Query query = entityManager.createNativeQuery(
				"UPDATE Users SET status=" + "'" + status + "'" + "WHERE username=" + "'" + username + "'");
		int result = query.executeUpdate();
		return result;
	}

	public User findUser(String username) throws loginException {
		User returnedUser = null;
		Query findUser = entityManager.createNativeQuery(
				"SELECT user_id, username, password, firstname, lastname, email, status FROM Users WHERE username ="
						+ "'" + username + "'",
				User.class);
		findUser.setParameter("username", username);

		try {
			returnedUser = (User) findUser.getSingleResult();
		} catch (Exception e) {
			if (returnedUser == null) {
				throw new loginException("Username does not exist");
			}
		}

		return returnedUser;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllNonDeletedUsers() {
		List<User> users = new ArrayList<User>();
		users = entityManager.createNativeQuery(
				"SELECT user_id, username, password, firstname, lastname, email, status FROM Users WHERE status != 'Deleted'",
				User.class).getResultList();
		return users;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllBrokers() {
		List<User> users = new ArrayList<User>();
		users = entityManager.createNativeQuery(
				"SELECT user_id, username, password, firstname, lastname, email, status FROM Users WHERE status = 'Broker'",
				User.class).getResultList();
		return users;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllAdmins() {
		List<User> users = new ArrayList<User>();
		users = entityManager.createNativeQuery(
				"SELECT user_id, username, password, firstname, lastname, email, status FROM Users WHERE status = 'Admin'",
				User.class).getResultList();
		return users;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllRegularUsers() {
		List<User> users = new ArrayList<User>();
		users = entityManager.createNativeQuery(
				"SELECT user_id, username, password, firstname, lastname, email, status FROM Users WHERE status = 'Regular User'",
				User.class).getResultList();
		return users;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllDeletedUsers() {
		List<User> users = new ArrayList<User>();
		users = entityManager.createNativeQuery(
				"SELECT user_id, username, password, firstname, lastname, email, status FROM Users WHERE status = 'Deleted'",
				User.class).getResultList();
		return users;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllActiveUsers() {
		List<User> users = new ArrayList<User>();
		users = entityManager.createNativeQuery(
				"SELECT user_id, username, password, firstname, lastname, email, status FROM Users WHERE status IN ('Admin', 'Broker','Regular User')",
				User.class).getResultList();
		return users;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllBannedUsers() {
		List<User> users = new ArrayList<User>();
		users = entityManager.createNativeQuery(
				"SELECT user_id, username, password, firstname, lastname, email, status FROM Users WHERE status IN ('Temporarily Banned', 'Permanently Banned')",
				User.class).getResultList();
		return users;
	}

	public User findUserID(int id) {
		User user = entityManager.find(User.class, id);

		if (user == null) {
			return null;
		}
		return user;
	}

	public User login(String username, String passwordEntered) throws loginException {

		User searchedUser = null;

		searchedUser = findUsername(username);

		if (searchedUser == null) {

			throw new loginException("<a href='register'>Invalid username, do you want to register?</a>");
		}

		String passwordInDatabase = searchedUser.getPassword();
		if ((!passwordInDatabase.equals(passwordEntered))) {
			throw new loginException("Password is incorrect");
		}
		if (passwordInDatabase.equals(passwordEntered) && !searchedUser.getStatus().equals("Deleted")) {
			return searchedUser;
		}
		if (searchedUser.getStatus().equals("Deleted"))
			throw new loginException("Sorry, it seems that this account has been deleted");
		else {
			return searchedUser;
		}
	}

	public User findUsername(String username) throws loginException {
		User returnedUser = null;
		Query findUser = entityManager.createNativeQuery(
				"SELECT user_id, username, password, firstname, lastname, email, status FROM Users WHERE username ="
						+ "'" + username + "'",
				User.class);
		findUser.setParameter("username", username);

		try {
			returnedUser = (User) findUser.getSingleResult();
		} catch (Exception e) {
			throw new loginException("<a href='register'>Invalid username, do you want to register?</a>");
		}
		return returnedUser;
	}

	public User updateUser(String username, String password, String firstName, String lastName, String email,
			String status) {
		if (username == null || password == null || firstName == null || lastName == null || email == null) {
			return null;
		}
		User user = null;
		try {
			user = findUsername(username);
		} catch (loginException e) {
			e.printStackTrace();
			return user;
		}
		user.setEmail(email);
		user.setFirstname(firstName);
		user.setLastname(lastName);
		user.setPassword(password);
		user.setUsername(username);
		entityManager.merge(user);
		return user;
	}

	public double getMoney(int user_id) {
		Query query1 = entityManager.createNativeQuery("SELECT money FROM wallets WHERE user_id = " + user_id);
		double money = (Double) query1.getSingleResult();
		return money;
	}

	public void setMoney(int user_id, double money) {
		Query query1 = entityManager
				.createNativeQuery("UPDATE wallets SET money = " + money + " WHERE user_id = " + user_id);
		query1.executeUpdate();
	}

	public Wallet getWallet(int user_id) {
		Query query1 = entityManager.createNativeQuery("SELECT user_id,money FROM wallets WHERE user_id = " + user_id,
				Wallet.class);
		return (Wallet) query1.getSingleResult();
	}

}
