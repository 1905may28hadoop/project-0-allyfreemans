package com.revature.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.revature.exception.AccountNotFoundException;
import com.revature.exception.TransactionNotFoundException;
import com.revature.exception.UserNotFoundException;
import com.revature.repository.AccountDAO;
import com.revature.repository.AccountDAOImpl;
import com.revature.repository.TransactionDAO;
import com.revature.repository.TransactionDAOImpl;
import com.revature.repository.UserDAO;
import com.revature.repository.UserDAOImpl;
import com.revature.service.CloseStreams;
import com.revature.service.ConnectionUtil;

public class UnitTests {

	private static final AccountDAO account = new AccountDAOImpl();
	private static final UserDAO user = new UserDAOImpl();
	private static final TransactionDAO transaction = new TransactionDAOImpl();

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void canConnect() {
		Connection conn = null;
		try {
			conn = ConnectionUtil.getConnection();
			assertEquals(true, conn.isValid(1000));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseStreams.close(conn);
		}
	}
	
	@Test
	public void canGetUsers() {
		try {
			assertTrue(user.getUserByID(1) != null);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void canGetAccounts() {
		try {
			assertTrue(account.getAccountByAccountID(1) != null);
		} catch (AccountNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void canGetTransactions() {
		try {
			assertTrue(transaction.getTransactionbyTransactionID(1) != null);
		} catch (TransactionNotFoundException e) {
			e.printStackTrace();
		}
	}
}
