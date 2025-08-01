package com.swabhav.transaction.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckBalance {

    public double checkBalance(String accountNumber, String password) {
        if (!isValidInput(accountNumber, password)) {
            return -1; 
        }

        try (Connection con = DButility.getConnection()) {
            String query = "SELECT username, balance FROM accounts WHERE account_number = ? AND password = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, accountNumber);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String username = rs.getString("username");
                double balance = rs.getDouble("balance");
          
                System.out.println(" Account verified successfully!");
                System.out.println(" Account Holder: " + username);
                System.out.println(" Account Number: " + accountNumber);
                System.out.println(" Current Balance: ₹" + balance);
                
                return balance;
            } else {
                System.out.println(" Invalid account number or password.");
                return -1;
            }

        } catch (SQLException e) {
            System.out.println(" Error fetching balance: " + e.getMessage());
            return -1;
        }
    }

    private boolean isValidInput(String accountNumber, String password) {
   
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            System.out.println(" Account number cannot be empty.");
            return false;
        }

        if (!accountNumber.matches("\\d{8,12}")) {
            System.out.println(" Invalid account number format. Must be 8-12 digits.");
            return false;
        }

      
        if (password == null || password.trim().isEmpty()) {
            System.out.println(" Password cannot be empty.");
            return false;
        }

        if (password.length() < 4) {
            System.out.println(" Password must be at least 4 characters long.");
            return false;
        }

        return true;
    }

    public boolean isAccountValid(String accountNumber, String password) {
        if (!isValidInput(accountNumber, password)) {
            return false;
        }

        try (Connection con = DButility.getConnection()) {
            String query = "SELECT 1 FROM accounts WHERE account_number = ? AND password = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, accountNumber);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            System.out.println(" Error validating account: " + e.getMessage());
            return false;
        }
    }

    public double getBalanceOnly(String accountNumber, String password) {
        if (!isValidInput(accountNumber, password)) {
            return -1;
        }

        try (Connection con = DButility.getConnection()) {
            String query = "SELECT balance FROM accounts WHERE account_number = ? AND password = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, accountNumber);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("balance");
            } else {
                return -1;
            }

        } catch (SQLException e) {
            System.out.println(" Error fetching balance: " + e.getMessage());
            return -1;
        }
    }
}