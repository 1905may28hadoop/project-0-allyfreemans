package com.revature.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.revature.controller.ControllerIns;

public class ConnectionUtil {

	private static Connection conn = null;
	
	private static Logger log = Logger.getLogger(ControllerIns.class);

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		try {

			Properties properties = new Properties();

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			properties.load(loader.getResourceAsStream("connection.properties"));

			String url = properties.getProperty("url");
			String username = properties.getProperty("username");
			String password = properties.getProperty("password");

			conn = DriverManager.getConnection(url, username, password);

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info("Connection established successfully.");
		return conn;
	}

}
