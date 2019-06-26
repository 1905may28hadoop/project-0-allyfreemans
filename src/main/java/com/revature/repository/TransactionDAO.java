package com.revature.repository;

import java.util.List;

import com.revature.exception.TransactionNotFoundException;
import com.revature.model.Transaction;

public interface TransactionDAO {
	
	Transaction getTransactionbyTransactionID(long id) throws TransactionNotFoundException;
	
	List<Transaction> getTransactionByAccountID(long id) throws TransactionNotFoundException;
	
	List <Transaction> getTransactions();
	
	boolean createTransaction(Transaction transaction);
	
	int getNextID();

}
