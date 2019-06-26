package com.revature.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

	private int userID;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Users [userID=" + userID + ", username=" + username + ", password=" + password + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}
	
	public User(int userID, String username, String password, String firstName, String lastName, String email) {
		super();
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public User(ResultSet resultSet) throws SQLException {
		this (
		resultSet.getInt("userID"),
		resultSet.getString("username"),
		resultSet.getString("password"),
		resultSet.getString("firstName"),
		resultSet.getString("lastName"),
		resultSet.getString("email") 
		);
	}

}