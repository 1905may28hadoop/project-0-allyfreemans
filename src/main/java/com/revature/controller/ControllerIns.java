package com.revature.controller;

import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.exception.AccountNotFoundException;
import com.revature.exception.InvalidPasswordException;
import com.revature.exception.TransactionNotFoundException;
import com.revature.exception.UserNotFoundException;
import com.revature.model.*;
import com.revature.repository.*;
import com.revature.service.*;

public class ControllerIns implements Controller {

	private Logger log = Logger.getLogger(ControllerIns.class);

	final int exitCondition = 10;

	int menuChoice = -1;
	int prevMenu = -1;

	String username;
	long id;
	String firstname;
	String lastname;
	String email;
	String password;

	Connection conn;

	UserDAO user = new UserDAOImpl();
	AccountDAO account = new AccountDAOImpl();
	TransactionDAO transaction = new TransactionDAOImpl();

	User currentUser = null;
	Account currentAccount = null;
	Transaction currentTransaction = null;

	@Override
	public void runMenu() {
		prevMenu = menuChoice;
		menuChoice = UserUtil.getUserInputInt();
	}

	@Override
	public void loginMenu() {
		System.out.println(" >> LOGIN MENU <<\n\n" + 
				" Username: ");
		username = UserUtil.getUserInputString();

		System.out.println(" Password: ");
		password = UserUtil.getUserInputString();

		System.out.println("\n Now attempting login.\n");

		try {
			conn = ConnectionUtil.getConnection();
			if (conn == null) {
				log.warn("Connection not valid");
				System.exit(-1);
			}
			System.out.println(" CONNECTION ESTABLISHED\n");
			System.out.println(" VERIFYING USER IN DATABASE\n");
			currentUser = user.getUserByUsername(username, password);

			id = currentUser.getUserID();
			currentAccount = account.getAccountByUserID(id);

			accountMenu();

		} catch (UserNotFoundException e) {
			log.warn("User not fetched");
			System.out.println("[!] User not found\n");
			CloseStreams.close(conn);
			landingMenu();
		}

		catch (InvalidPasswordException e) {
			log.info("Invalid password entered");
			System.out.println("[!] Invalid password\n");
			CloseStreams.close(conn);
			landingMenu();
		}

		catch (AccountNotFoundException e) {
			log.warn("Account not fetched");
			System.out.println("[!] Account not found\n");
			CloseStreams.close(conn);
			landingMenu();
		} finally {
			CloseStreams.close(conn);
		}
	}

	@Override
	public void accountMenu() {
		System.out.println("\n >> ACCOUNT MENU <<\n" + " Select a menu option by typing a number.\n" + "\n" + " 1: View balance\n"
				+ " 2: Withdraw Money\n" + " 3: Deposit Money\n"
				+ " 4: View transactions for this account\n" + " 5: Log Out\n");
		runMenu();
		try {
			switch (menuChoice) {
			case 1:
				balanceMenu();
				break;
			case 2:
				withdrawMenu();
				break;
			case 3:
				depositMenu();
				break;
			case 4:
				transactionMenu();
				break;
			case 5:
				System.out.println(" Logging out . . .\n");
				CloseStreams.close(conn);
				landingMenu();
				break;
			default:
				goodbye();
			}
		} catch (InputMismatchException e) {
			System.out.println(" Invalid entry selected. Exiting . . .");
		}

	}

	@Override
	public void transactionMenu() {
		System.out.println("\n >> TRANSACTIONS MENU <<\n\n"
				+ "-----------------------------------------------------------------------------\n"
				+ "   DATE   |    W/D   | ID |     TIME      |   ACCOUNT #   | AMOUNT | BALANCE");

		List<Transaction> transactionList = null;
		try {
			transactionList = transaction.getTransactionByAccountID(currentAccount.getAccountID());
		} catch (TransactionNotFoundException e) {
			log.warn("No transaction found");
		}

		for (Transaction t : transactionList) {
			System.out.println(t.toString());
		}
		System.out.println("-----------------------------------------------------------------------------\n" + "\n");

		accountMenu();
	}

	@Override
	public void balanceMenu() {
		System.out.println(" >> BALANCE MENU <<\n" + "\n" + " Hello, USER "
				+ currentAccount.getUserID() + "!\n" + " Your current balance is:");

		try {
			currentAccount = account.getAccountByUserID(id);
		} catch (AccountNotFoundException e) {
			log.warn("Current account is invalid.");
			System.out.println("Invalid account.");
		}

		System.out.println(
				String.format(" %.2f", currentAccount.getBalance()) + " " + currentAccount.getCurrencyType());
		System.out.println("");
		
		accountMenu();
	}

	@Override
	public void withdrawMenu() {
		System.out.println("\n >> WITHDRAWL MENU <<\n" + 
				" Please enter the amount in the format ##.##\n");

		float withdrawlAmt = UserUtil.getUserInputFloat();
		float currentBal = (float) currentAccount.getBalance();

		try {
			currentAccount = account.getAccountByUserID(id);
		} catch (AccountNotFoundException e1) {
			log.warn("Current account is invalid.");
			System.out.println(" Invalid account.");
		}

		System.out.println(" Attemping to withdraw " + String.format("%.2f", withdrawlAmt) + " "
				+ currentAccount.getCurrencyType() + " from account " + currentAccount.getAccountID());

		currentTransaction = new Transaction(0, "", withdrawlAmt * -1, currentBal, currentBal - withdrawlAmt,
				currentAccount.getAccountID());
		transaction.createTransaction(currentTransaction);
		log.info("Created transaction record");

		account.updateBalance(currentAccount, (float) currentAccount.getBalance() - withdrawlAmt);
		try {
			currentAccount = account.getAccountByUserID(id);
		} catch (AccountNotFoundException e) {
			log.warn("Current account is invalid.");
			System.out.println(" Invalid account.");
		}

		System.out.println(" Success! Your account balance is now "
				+ String.format("%.2f", currentAccount.getBalance()) + " " + currentAccount.getCurrencyType() + ".");

		accountMenu();
	}

	@Override
	public void depositMenu() {
		System.out.println(" >> DEPOSIT MENU <<\r\n" + 
				" Please enter the amount in the format ##.##");

		float withdrawlAmt = UserUtil.getUserInputFloat();
		float currentBal = (float) currentAccount.getBalance();

		try {
			currentAccount = account.getAccountByUserID(id);
		} catch (AccountNotFoundException e1) {
			log.warn("Current account is invalid.");
			System.out.println(" Invalid account.");
		}

		System.out.println("  Attemping to deposit " + String.format("%.2f", withdrawlAmt) + " "
				+ currentAccount.getCurrencyType() + " to account " + currentAccount.getAccountID());

		currentTransaction = new Transaction(0, "", withdrawlAmt, currentBal, currentBal + withdrawlAmt,
				currentAccount.getAccountID());
		transaction.createTransaction(currentTransaction);
		log.info("Created transaction record");

		account.updateBalance(currentAccount, (float) currentAccount.getBalance() + withdrawlAmt);
		try {
			currentAccount = account.getAccountByUserID(id);
		} catch (AccountNotFoundException e) {
			log.warn("Current account is invalid.");
			System.out.println("Invalid account.");
		}

		System.out.println(" Success! Your account balance is now "
				+ String.format("%.2f", currentAccount.getBalance()) + " " + currentAccount.getCurrencyType() + ".");

		accountMenu();
	}

	@Override
	public void newAccountMenu() {

		System.out.println("\n >> NEW ACCOUNT MENU <<\r\n" + 
				" \r\n" + 
				" Firstname (no spaces):");
		firstname = UserUtil.getUserInputString();

		System.out.println(" Lastname (no spaces):");
		lastname = UserUtil.getUserInputString();

		System.out.println(" Email (no spaces):");
		email = UserUtil.getUserInputString();

		System.out.println(" Username (no spaces):");
		username = UserUtil.getUserInputString();

		System.out.println(" Password (no spaces):");
		password = UserUtil.getUserInputString();

		System.out.println("\n Creating new account for " + username + ".");

		currentUser = new User(user.getNextID(), username, password, firstname, lastname, email);
		currentAccount = new Account(0, 0.00, "USD", currentUser.getUserID());

		if (user.createUser(currentUser)) {
			System.out.println("\n Made new user successfully!");
		} else {
			log.warn("Current user could not be created.");
			System.out.println("\n [X] User not created.\r\n" + " Your username or email are already in use.\n");
			currentUser = null;
		}

		if (account.createAccount(currentAccount) && currentUser != null) {
			System.out.println("\n Made new account for user successfully!\n Returning to main menu . . .\n");
		} else if (currentUser != null) {
			log.warn("Current account could not be created.");
			System.out.println("\n [X] Account creation error.\r\n" + " Please try again!\n");
		}

		System.out.println("");
		landingMenu();
	}

	@Override
	public void debugMenu() {
		log.info("debug menu");
	}

	@Override
	public void landingMenu() {
		System.out.println(
				" >> MAIN MENU <<\n" + 
				" Select a menu option by typing a number.\n" + 
				" 1: Login\n" + 
				" 2: New Account\n" + 
				" 3: Exit");
		
		try {
			runMenu();
			switch (menuChoice) {
			case 0:
				debugMenu();
				break;
			case 1:
				loginMenu();
				break;
			case 2:
				newAccountMenu();
				break;
			default:
				goodbye();
			}
		} catch (InputMismatchException e) {
			System.out.println(" Invalid entry selected.\n Exiting . . .");
		}
	}

	@Override
	public void goodbye() {
		System.out.println(" Goodbye!");
		System.exit(1);
	}

}
