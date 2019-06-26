package com.revature.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.revature.controller.ControllerIns;

public class CloseStreams {
	
	private static Logger log = Logger.getLogger(CloseStreams.class);

	public static void close(FileInputStream resource) {
		if (resource != null)
			try {
				resource.close();
				log.info("Resource close success.");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public static void close(Connection resource) {
		if (resource != null)
			try {
				resource.close();
				log.info("Resource close success.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static void close(Statement resource) {
		if (resource != null)
			try {
				resource.close();
				log.info("Resource close success.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static void close(ResultSet resource) {
		if (resource != null)
			try {
				resource.close();
				log.info("Resource close success.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

}