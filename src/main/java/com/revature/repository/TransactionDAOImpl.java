package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.exception.TransactionNotFoundException;
import com.revature.model.Transaction;
import com.revature.service.CloseStreams;
import com.revature.service.ConnectionUtil;

public class TransactionDAOImpl implements TransactionDAO {
	
	private Connection conn = null;
	private static Logger log = Logger.getLogger(CloseStreams.class);

	@Override
	public Transaction getTransactionbyTransactionID(long id) throws TransactionNotFoundException{
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Transaction transaction = null;
		try {
			conn = ConnectionUtil.getConnection();
			statement = conn.prepareStatement("SELECT * FROM transactions WHERE transactionid = ?");
			statement.setLong(1, id);
			statement.execute();
			resultSet = statement.getResultSet();
			resultSet.next();
			transaction = new Transaction(
					resultSet.getInt("transactionid"),
					resultSet.getString("timestamp"),
					resultSet.getDouble("amount"), 
					resultSet.getDouble("balanceBefore"), 
					resultSet.getDouble("balanceAfter"), 
					resultSet.getInt("accountid"));
		} catch (SQLException e) {
			throw new TransactionNotFoundException("Transaction not found");
		} finally {
			CloseStreams.close(statement);
			CloseStreams.close(resultSet);
			CloseStreams.close(conn);
		}
		log.info("Returning transaction object.");
		return transaction;
	}

	@Override
	public List<Transaction> getTransactions() {
		Statement statement = null;
		ResultSet resultSet = null;
		List<Transaction> transactions = new ArrayList<Transaction>();
		try {
			conn = ConnectionUtil.getConnection();

			statement = conn.createStatement();

			resultSet = statement.executeQuery("SELECT * FROM transactions ORDER BY timestamp");

			while (resultSet.next()) { // goes to the first result
				transactions.add(new Transaction(resultSet));
			}
		} catch (SQLException e) {
		} finally {
			CloseStreams.close(statement);
			CloseStreams.close(resultSet);
			CloseStreams.close(conn);
		}
		log.info("Returning transaction object.");
		return transactions;
	}

	@Override
	public List<Transaction> getTransactionByAccountID(long id) throws TransactionNotFoundException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Transaction> transactions = new ArrayList<Transaction>();
		try {
			conn = ConnectionUtil.getConnection();

			statement = conn.prepareStatement("SELECT * FROM transactions WHERE accountid = ? ORDER BY timestamp");
			statement.setLong(1, id);
			statement.execute();
			resultSet = statement.getResultSet();

			while (resultSet.next()) { // goes to the first result
				transactions.add(new Transaction(resultSet));
			}
		} catch (SQLException e) {
			throw new TransactionNotFoundException("Transaction not found");
		} finally {
			CloseStreams.close(statement);
			CloseStreams.close(resultSet);
			CloseStreams.close(conn);
		}
		log.info("Returning transaction object.");
		return transactions;
	}

	@Override
	public boolean createTransaction(Transaction transaction) {
		PreparedStatement statement = null;
		String timeStamp = new SimpleDateFormat("dd-MMM-yy hh.mm.ss.SSSSSSSSS aa").format(new Date());
		try {
			conn = ConnectionUtil.getConnection();
			statement = conn.prepareStatement("INSERT INTO transactions VALUES(?,?,?,?,?,?)");
			statement.setInt(1, getNextID());
			statement.setString(2, timeStamp);
			statement.setDouble(3, transaction.getAmount());
			statement.setDouble(4, transaction.getBalanceBefore());
			statement.setDouble(5, transaction.getBalanceAfter());
			statement.setLong(6, transaction.getAccountID());
			statement.execute();
		} catch (SQLException e) {
			return false;
		} finally {
			CloseStreams.close(statement);
			CloseStreams.close(conn);
		}
		log.info("Created transaction.");
		return true;
	}

	@Override
	public int getNextID() {
		List<Transaction> transactionList = getTransactions();
		if(transactionList == null)
			return 0;
		return transactionList.size()+1;
	}

}
