package com.swabhav.transaction.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
	private String username;
	private String accountNumber;
	private String password;
	private double balance;

	public Account(String username, String accountNumber, String password, double balance) {
		this.username = username;
		this.accountNumber = accountNumber;
		this.password = password;
		this.balance = balance;
	}

	public String getUsername() {
		return username;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getPassword() {
		return password;
	}

	public double getBalance() {
		return balance;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account Details : [username=" + username + ", accountNumber=" + accountNumber + ", password=" + password
				+ ", balance=" + balance + "]";
	}

	public static boolean isValidAccountNumber(String accountNumber) {
		if (accountNumber == null || accountNumber.trim().isEmpty()) {
			return false;
		}
		return accountNumber.matches("\\d{8,12}");
	}

	public static boolean isAccountNumberExists(String accountNumber) {
		try (Connection con = DButility.getConnection()) {
			String query = "SELECT COUNT(*) FROM accounts WHERE account_number = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, accountNumber);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			System.out.println("Error checking account existence: " + e.getMessage());
		}
		return false;
	}
	
	public static boolean validateAccountPassword(String accountNumber, String password) {
	    try (Connection con = DButility.getConnection()) {
	        String query = "SELECT 1 FROM accounts WHERE account_number = ? AND password = ?";
	        PreparedStatement stmt = con.prepareStatement(query);
	        stmt.setString(1, accountNumber);
	        stmt.setString(2, password);
	        ResultSet rs = stmt.executeQuery();
	        return rs.next();
	    } catch (SQLException e) {
	        System.out.println("Error validating password: " + e.getMessage());
	        return false;
	    }
	}


	public boolean saveToDatabase() {
		try (Connection con = DButility.getConnection()) {
			String query = "INSERT INTO accounts (username, account_number, password, balance) VALUES (?, ?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, this.username);
			stmt.setString(2, this.accountNumber);
			stmt.setString(3, this.password);
			stmt.setDouble(4, this.balance);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			System.out.println("Error saving account: " + e.getMessage());
			return false;
		}
	}

	public boolean validatePassword(String inputPassword) {
		return this.password != null && this.password.equals(inputPassword);
	}

	public boolean canCreateAccount() {
		if (!isValidAccountNumber(this.accountNumber)) {
			System.out.println(" Invalid account number format. Must be 8-12 digits.");
			return false;
		}
		if (isAccountNumberExists(this.accountNumber)) {
			System.out.println("Account with number " + this.accountNumber + " already exists.");
			return false;
		}
		if (!isValidUsername(this.username)) {
			System.out.println(" Invalid username.");
			return false;
		}
		if (!isValidBalance(this.balance)) {
			System.out.println(" Invalid balance.");
			return false;
		}
		if (this.password == null || this.password.length() < 4) {
			System.out.println(" Password must be of 4 characters long");
			return false;
		}
		return true;
	}

	public static boolean isValidUsername(String username) {
		if (username == null || username.trim().isEmpty()) {
			return false;
		}
		return username.matches("[a-zA-Z\\s]+");
	}

	public static boolean isValidBalance(double balance) {
		return balance >= 0 && balance <= 100000;
	}

	public boolean isValidAccount() {
		return isValidAccountNumber(this.accountNumber) && isValidUsername(this.username)
				&& isValidBalance(this.balance) && this.password != null && this.password.length() >= 4;
	}

	public static boolean isValidPassword(String password2) {
		return true;
	}

	public static Account getAccountByNumber(String accountNumber) {
		try (Connection con = DButility.getConnection()) {
			String query = "SELECT * FROM accounts WHERE account_number = ?";
			PreparedStatement stmt = con.prepareStatement(query);
			stmt.setString(1, accountNumber);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				double balance = rs.getDouble("balance");
				return new Account(username, accountNumber, password, balance);
			}
		} catch (SQLException e) {
			System.out.println("Error fetching account: " + e.getMessage());
		}
		return null;
	}
}
