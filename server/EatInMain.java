package com.linkedin.eatin;
//import java.sql.Date;

import java.sql.DriverManager;
import java.sql.Connection;

import java.sql.SQLException;



public class EatInMain {

	public static Connection connection;
	
	public static void main(String[] argv) {
		connection = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}

		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://lhuang-ld.linkedin.biz:3306/EatIn", "root",
					"eatin");
			
			Helper h = new Helper(connection);
			//Helper.populateDB();
			h.getComments(1, 4);
			h.getWeeksMenu();
			

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}

	
}