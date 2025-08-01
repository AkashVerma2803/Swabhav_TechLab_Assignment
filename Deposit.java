package com.swabhav.transaction.model;

import java.sql.*;

public class Deposit {

    public boolean deposit(String accountNumber, double amount, String password) {
        if (!isValidDeposit(accountNumber, amount, password)) {
            return false;
        }

        try (Connection con = DButility.getConnection()) {
            double currentBalance = validateAccountAndGetBalance(con, accountNumber, password);
            if (currentBalance == -1) {
                return false;
            }

            String updateQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
            PreparedStatement updateStmt = con.prepareStatement(updateQuery);
            updateStmt.setDouble(1, amount);
            updateStmt.setString(2, accountNumber);
            int rowsUpdated = updateStmt.executeUpdate();

            if (rowsUpdated > 0) {
                double newBalance = currentBalance + amount;
                System.out.println("Money : ₹" + amount + " deposited successfully!");
                System.out.println("New Balance: ₹" + newBalance);

                logTransaction(con, accountNumber, "DEPOSIT", amount, null, null, "Deposit money");

                return true;
            } else {
                System.out.println("Deposit failed due to technical error.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error during deposit: " + e.getMessage());
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


    private boolean isValidDeposit(String accountNumber, double amount, String password) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            System.out.println("Invalid account number.");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Deposit amount must be greater than zero.");
            return false;
        }
        if (password == null || password.trim().isEmpty()) {
            System.out.println("Password cannot be empty.");
            return false;
        }
        return true;
    }
}
