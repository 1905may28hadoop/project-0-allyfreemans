package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.exception.AccountNotFoundException;
import com.revature.model.Account;
import com.revature.service.CloseStreams;
import com.revature.service.ConnectionUtil;

public class AccountDAOImpl implements AccountDAO {
	
	private Connection conn = null;
	private static Logger log = Logger.getLogger(CloseStreams.class);

	@Override
	public Account getAccountByAccountID(long id) throws AccountNotFoundException{
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Account account = null;
		try {
			conn = ConnectionUtil.getConnection();
			statement = conn.prepareStatement("SELECT * FROM accounts WHERE accountid = ?");
			statement.setLong(1, id);
			statement.execute();
			resultSet = statement.getResultSet();
			resultSet.next(); // goes to the first result
			account = new Account(
					resultSet.getInt("accountID"), 
					resultSet.getDouble("balance"),
					resultSet.getString("currencyType"),
					resultSet.getInt("userID")
					);
		} catch (SQLException e) {
			throw new AccountNotFoundException("Account not found.");
		}  finally {
			CloseStreams.close(statement);
			CloseStreams.close(resultSet);
			CloseStreams.close(conn);
		}
		log.info("Returning account object.");
		return account;
	}

	@Override
	public Account getAccountByUserID(long id) throws AccountNotFoundException{
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Account account = null;
		try {
			conn = ConnectionUtil.getConnection();
			statement = conn.prepareStatement("SELECT * FROM accounts WHERE userid = ?");
			statement.setLong(1, id);
			statement.execute();
			resultSet = statement.getResultSet();
			resultSet.next(); // goes to the first result
			account = new Account(
					resultSet.getInt("accountID"),
					resultSet.getDouble("balance"),
					resultSet.getString("currencyType"),
					resultSet.getInt("userID")
					);
		} catch (SQLException e) {
			throw new AccountNotFoundException("Account not found");
		}  finally {
			CloseStreams.close(statement);
			CloseStreams.close(resultSet);
			CloseStreams.close(conn);
		}
		log.info("Returning account object.");
		return account;
	}

	@Override
	public boolean createAccount(Account account) {
		PreparedStatement statement = null;
		try {
			conn = ConnectionUtil.getConnection();
			statement = conn.prepareStatement("INSERT INTO accounts VALUES(?,?,?,?)");
			statement.setInt(1, getNextID()+1);
			statement.setLong(2, (long) account.getBalance());
			statement.setString(3, account.getCurrencyType());
			statement.setLong(4, account.getUserID());
			statement.executeUpdate();
		} catch (SQLException e) {
			return false;
		} finally {
			CloseStreams.close(statement);
			CloseStreams.close(conn);
		}
		log.info("created account object.");
		return true;
	}

	@Override
	public boolean updateAccount(Account account) {
		PreparedStatement statement = null;
		try {
			conn = ConnectionUtil.getConnection();
			statement = conn.prepareStatement("UPDATE accounts SET balance = ? WHERE accountid = ?");
			statement.setDouble(1, account.getBalance());
			statement.setLong(2, account.getAccountID());
			System.out.println("update resulted in "+statement.execute());
			System.out.println("update execute resulted in "+statement.executeUpdate());
		} catch (SQLException e) {
			return false;
		} finally {
			CloseStreams.close(statement);
			CloseStreams.close(conn);
		}
		log.info("created account object.");
		return true;
	}

	@Override
	public boolean updateBalance(Account account, float balance) {
		PreparedStatement statement = null;
		try {
			conn = ConnectionUtil.getConnection();
			statement = conn.prepareStatement("UPDATE accounts SET balance = ? WHERE accountid = ?");
			statement.setDouble(1, balance);
			statement.setLong(2, account.getAccountID());
			statement.execute();
		} catch (SQLException e) {
			return false;
		} finally {
			CloseStreams.close(statement);
			CloseStreams.close(conn);
		}
		log.info("created account object.");
		return true;
	}

	@Override
	public List<Account> getAccounts() {
		Statement statement = null;
		ResultSet resultSet = null;
		List<Account> accounts = new ArrayList<Account>();
		try {
			conn = ConnectionUtil.getConnection();

			statement = conn.createStatement();

			resultSet = statement.executeQuery("SELECT * FROM accounts");

			while (resultSet.next()) { // goes to the first result
				accounts.add(new Account(0, 0, null, 0));
			}
		} catch (SQLException e) {
		} finally {
			CloseStreams.close(statement);
			CloseStreams.close(resultSet);
			CloseStreams.close(conn);
		}
		log.info("Returning account object.");
		return accounts;
	}

	@Override
	public int getNextID() {
		List<Account> accountList = getAccounts();
		return accountList.size();
	}

}
