package com.revature.model;

public class Account {

	private int accountID;
	private double balance;
	private String currencyType;
	private int userID;

	public Account(int accountID, double balance, String currencyType, int userID) {
		super();
		this.accountID = accountID;
		this.balance = balance;
		this.currencyType = currencyType;
		this.userID = userID;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	@Override
	public String toString() {
		return "Accounts [accountID=" + accountID + ", balance=" + balance + ", currencyType=" + currencyType
				+ ", userID=" + userID + "]";
	}

}
