package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.exception.InvalidPasswordException;
import com.revature.exception.UserNotFoundException;
import com.revature.model.User;
import com.revature.service.CloseStreams;
import com.revature.service.ConnectionUtil;

public class UserDAOImpl implements UserDAO {
	
	private Connection conn = null;
	private static Logger log = Logger.getLogger(CloseStreams.class);

	@Override
	public User getUserByID(long id) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		User user = null;
		conn =  ConnectionUtil.getConnection();
		try {
			statement = conn.prepareStatement("SELECT * FROM users WHERE userid = ?");
			statement.setLong(1, id);
			statement.execute();
			resultSet = statement.getResultSet();
			resultSet.next(); // goes to the first result
			user = new User(
					resultSet.getInt("userid"), 
					resultSet.getString("username"),
					resultSet.getString("password"), 
					resultSet.getString("firstName"), 
					resultSet.getString("lastName"),
					resultSet.getString("email"));
			
		} catch (SQLException e) {
			return null;
		}  finally {
			CloseStreams.close(statement);
			CloseStreams.close(resultSet);
			CloseStreams.close(conn);
		}
		log.info("Returning user object.");
		return user;
	}

	@Override
	public boolean createUser(User user) {
		PreparedStatement statement = null;
		try {
			conn = ConnectionUtil.getConnection();
			statement = conn.prepareStatement("INSERT INTO users VALUES(?,?,?,?,?,?)");
			statement.setInt(1, getNextID());
			statement.setString(2, user.getUsername());
			statement.setString(3, user.getPassword());
			statement.setString(4, user.getFirstName());
			statement.setString(5, user.getLastName());
			statement.setString(6, user.getEmail());
			statement.execute();
			
		} catch (SQLException e) {
			return false;
		} finally {
			CloseStreams.close(statement);
			CloseStreams.close(conn);
		}
		log.info("Updating user object successful.");
		return true;
	}

	@Override
	public boolean updateUser(User user) {
		PreparedStatement statement = null;
		try {
			conn = ConnectionUtil.getConnection();
			statement = conn.prepareStatement("UPDATE users SET username = ?, password = ?, firstname = ?, lastname = ? WHERE userid = ?");
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getLastName());
			statement.setString(5, user.getEmail());
			statement.setLong(6, user.getUserID());
			
		} catch (SQLException e) {
			return false;
		} finally {
			CloseStreams.close(statement);
			CloseStreams.close(conn);
		}
		log.info("Updating user object successful.");
		return true;
	}

	@Override
	public List<User> getUsers() {
		Statement statement = null;
		ResultSet resultSet = null;
		List<User> users = new ArrayList<User>();
		try {
			conn = ConnectionUtil.getConnection();

			statement = conn.createStatement();

			resultSet = statement.executeQuery("SELECT * FROM users");

			while (resultSet.next()) { // goes to the first result
				users.add(new User(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseStreams.close(statement);
			CloseStreams.close(resultSet);
			CloseStreams.close(conn);
		}
		log.info("Returning user object.");
		return users;
	}
	
	@Override
	public User getUserByUsername(String username, String password) throws InvalidPasswordException, UserNotFoundException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		User user = null;
		conn =  ConnectionUtil.getConnection();
		try {
			statement = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
			statement.setString(1, username);
			statement.execute();
			resultSet = statement.getResultSet();
			resultSet.next(); // goes to the first result
			user = new User(
					resultSet.getInt("userid"), 
					resultSet.getString("username"),
					resultSet.getString("password"), 
					resultSet.getString("firstName"), 
					resultSet.getString("lastName"),
					resultSet.getString("email"));
			
		} catch (SQLException e) {
			throw new UserNotFoundException("Invalid user");
		}  finally {
			CloseStreams.close(statement);
			CloseStreams.close(resultSet);
			CloseStreams.close(conn);
		}
		if(!user.getPassword().equals(password))
			throw new InvalidPasswordException("Invalid password");
		log.info("Returning user object.");
		return user;
	}

	@Override
	public int getNextID() {
		List<User> userList = getUsers();
		
		if(userList == null)
			return 0;
		return userList.size()+1;
	}

}
