package com.revature.repository;

import java.util.List;

import com.revature.exception.InvalidPasswordException;
import com.revature.exception.UserNotFoundException;
import com.revature.model.User;

public interface UserDAO {
	
	User getUserByID(long id) throws UserNotFoundException;
	
	User getUserByUsername(String username, String password) throws UserNotFoundException, InvalidPasswordException;

	List <User> getUsers();
	
	boolean createUser(User user);
	
	boolean updateUser(User user);
	
	int getNextID();
	
}
