package com.revature.repository;

import java.util.List;

import com.revature.exception.AccountNotFoundException;
import com.revature.model.Account;

public interface AccountDAO {
	
	Account getAccountByAccountID(long id) throws AccountNotFoundException;
	
	Account getAccountByUserID(long id) throws AccountNotFoundException;
	
	List <Account> getAccounts();
	
	boolean createAccount(Account account);
	
	boolean updateAccount(Account account);
	
	boolean updateBalance(Account account, float balance);
	
	int getNextID();

}
