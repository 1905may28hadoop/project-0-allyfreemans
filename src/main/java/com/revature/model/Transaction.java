package com.revature.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

	private int transactionID;
	private String timestamp;
	private double amount;
	private double balanceBefore;
	private double balanceAfter;
	private int accountID;

	public Transaction(int transactionID, String timestamp, double amount, double balanceBefore, double balanceAfter,
			int accountID) {
		super();
		this.transactionID = transactionID;
		this.timestamp = timestamp;
		this.amount = amount;
		this.balanceBefore = balanceBefore;
		this.balanceAfter = balanceAfter;
		this.accountID = accountID;
	}

	public Transaction(ResultSet resultSet) throws SQLException {
		this (
		resultSet.getInt("transactionID"),
		resultSet.getString("timestamp"),
		resultSet.getDouble("amount"),
		resultSet.getDouble("balanceBefore"),
		resultSet.getDouble("balanceAfter"),
		resultSet.getInt("accountID")
		);
	}

	public int getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getBalanceBefore() {
		return balanceBefore;
	}

	public void setBalanceBefore(double balanceBefore) {
		this.balanceBefore = balanceBefore;
	}

	public double getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(double balanceAfter) {
		this.balanceAfter = balanceAfter;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	@Override
	public String toString() {
		Date date = new Date();
		Timestamp ts = Timestamp.valueOf(timestamp);
		date.setTime(ts.getTime());
		String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
		String formattedDate2 = new SimpleDateFormat("HH:mm:ss z").format(date);
		DecimalFormat df = new DecimalFormat("0.00");
		String returnString = formattedDate + "  ";
		if(amount < 00.00) {
			returnString = returnString + "Withdrawl ID "+transactionID+" ";
			returnString = returnString + "at "+formattedDate2+" ";
			returnString = returnString + " for Account "+accountID+"  ";
			returnString = returnString + "("+df.format(amount*-1)+")  "+df.format(balanceAfter);
		}
		else {
			returnString = returnString + "Deposit   ID "+transactionID+" ";
			returnString = returnString + "at "+formattedDate2+" ";
			returnString = returnString + " for Account "+accountID+"  ";
			returnString = returnString + " "+df.format(amount)+"   "+df.format(balanceAfter);
		}
		return returnString;
	}

}
