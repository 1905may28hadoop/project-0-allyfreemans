package com.revature.service;

import java.util.Scanner;

public class UserUtil {

	private static Scanner input = new Scanner(System.in);

	public static int getUserInputInt() {
		String s = null;
		int i = -1;
		
		while (i == -1) {
			try {
				s = getUserInputString();
				i = Integer.parseInt(s);
				
				if (i >= 1000000.00)
					throw new NumberFormatException();
				return i;
			} catch (NumberFormatException e) {
				if (i >= 1000000.00)
					System.out.println("That number is too large. Try again:");
				else
					System.out.println("Please enter a valid entry. Try again:");
			}
		}
		return i;
	}

	public static String getUserInputString() {
		return input.next();
	}

	public static double getUserInputDouble() {
		String s = null;
		double i = -1;
		
		while (i == -1) {
			try {
				s = getUserInputString();
				i = Double.parseDouble(s);
				
				if (i >= 1000000.00)
					throw new NumberFormatException();
				return i;
			} catch (NumberFormatException e) {
				if (i >= 1000000.00)
					System.out.println("That number is too large. Try again:");
				else
					System.out.println("Please enter a valid entry. Try again:");
			}
		}
		return i;
	}

	public static float getUserInputFloat() {
		String s = null;
		float i = -1;
		
		while (i == -1) {
			try {
				s = getUserInputString();
				i = Float.parseFloat(s);
				
				if (i >= 1000000.00)
					throw new NumberFormatException();
				return i;
			} catch (NumberFormatException e) {
				if (i >= 1000000.00)
					System.out.println("That number is too large. Try again:");
				else
					System.out.println("Please enter a valid entry. Try again:");
			}
		}
		return i;
	}
}
