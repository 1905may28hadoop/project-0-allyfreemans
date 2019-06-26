package com.revature.controller;

public interface Controller {
	
	// main method to run the menu functionality
	// it just calls the other methods until exit condition is reached
	void runMenu();
	
	// display main entry menu
	void landingMenu();
	
	// login menu functionality
	void loginMenu();
	
	// account menu functionality
	void accountMenu();
	
	// transaction menu functionality
	void transactionMenu();
	
	// balance menu functionality
	void balanceMenu();
	
	// Withdrawal menu functionality
	void withdrawMenu();
	
	// deposit menu functionality
	void depositMenu();
	
	// new account menu functionality
	void newAccountMenu();
	
	// debug menu functionality
	void debugMenu();
	
	// goodbye menu popup
	void goodbye();

}
