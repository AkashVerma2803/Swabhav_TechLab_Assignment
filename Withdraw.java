package com.swabhav.transaction.model;

import java.sql.*;

public class Withdraw {

    public boolean withdraw(String accountNumber, double amount, String password) {
        if (!isValidWithdrawal(accountNumber, amount, password)) {
            return false;
        
        }

        try (Connection con = DButility.getConnection()) {
            double currentBalance = validateAccountAndGetBalance(con, accountNumber, password);
            if (currentBalance == -1) {
                return false;
            }

            if (currentBalance < amount) {
                System.out.println("😭😭 Insufficient Balance!");
                return false;
            }

            String updateQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
            PreparedStatement updateStmt = con.prepareStatement(updateQuery);
            updateStmt.setDouble(1, amount);
            updateStmt.setString(2, accountNumber);

            int rowsUpdated = updateStmt.executeUpdate();

            if (rowsUpdated > 0) {
                double newBalance = currentBalance - amount;
                System.out.println("Money : ₹" + amount + " withdrawn successfully!");
                System.out.println("Remaining Balance: ₹" + newBalance);

                logTransaction(con, accountNumber, "WITHDRAWAL", amount, null, null, "Withdrawal money");

                return true;
            } else {
                System.out.println("Withdrawal failed due to technical error.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error during withdrawal: " + e.getMessage());
            return false;
        }
    }

    private void logTransaction(Connection con, String accountNumber, String transactionType, double amount, String fromAccount, String toAccount, String description) throws SQLException {
        String insertTransactionQuery = "INSERT INTO transactions (account_number, transaction_type, amount, from_account, to_account, description) " +
                                        "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(insertTransactionQuery)) {
            stmt.setString(1, accountNumber);
            stmt.setString(2, transactionType);
            stmt.setDouble(3, amount);
            stmt.setString(4, fromAccount);
            stmt.setString(5, toAccount);
            stmt.setString(6, description);
            stmt.executeUpdate();
        }
    }


    private double validateAccountAndGetBalance(Connection con, String accountNumber, String password) throws SQLException {
        String query = "SELECT balance FROM accounts WHERE account_number = ? AND password = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, accountNumber);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                } else {
                    System.out.println("Invalid account number or password.");
                    return -1;
                }
            }
        }
    }
 private boolean isValidWithdrawal(String accountNumber, double amount, String password) {
                if (accountNumber == null || accountNumber.trim().isEmpty()) {
                    System.out.println("Account number cannot be empty.");
                    return false;
                }
                if (password == null || password.trim().isEmpty()) {
                    System.out.println("Password cannot be empty.");
                    return false;
                }
                if (amount <= 0) {
                    System.out.println("Withdrawal amount must be greater than zero.");
                    return false;
                }
                return true;
            }
  
}
