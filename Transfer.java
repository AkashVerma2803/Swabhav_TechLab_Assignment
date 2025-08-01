package com.swabhav.transaction.model;

import java.sql.*;

public class Transfer {

    public boolean transfer(String senderAccount, String receiverAccount, double amount, String password) {
        if (!isValidTransfer(senderAccount, receiverAccount, amount, password)) {
            return false;
        }

        try (Connection con = DButility.getConnection()) {
            double senderBalance = validateAccountAndGetBalance(con, senderAccount, password);
            if (senderBalance == -1) {
                return false;
            }

            if (senderBalance < amount) {
                System.out.println("😭😭 Insufficient Balance!");
                return false;
            }

            String updateSenderQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
            PreparedStatement updateSenderStmt = con.prepareStatement(updateSenderQuery);
            updateSenderStmt.setDouble(1, amount);
            updateSenderStmt.setString(2, senderAccount);
            updateSenderStmt.executeUpdate();

            String updateReceiverQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
            PreparedStatement updateReceiverStmt = con.prepareStatement(updateReceiverQuery);
            updateReceiverStmt.setDouble(1, amount);
            updateReceiverStmt.setString(2, receiverAccount);
            updateReceiverStmt.executeUpdate();

           
            logTransaction(con, senderAccount, "TRANSFER_OUT", amount, senderAccount, receiverAccount, "Money sent to " + receiverAccount);
            logTransaction(con, receiverAccount, "TRANSFER_IN", amount, senderAccount, receiverAccount, "Money received from " + senderAccount);

            System.out.println("Money 💸💸 ₹" + amount + " transferred successfully from Account " + senderAccount + " to Account " + receiverAccount);
            return true;

        } catch (SQLException e) {
            System.out.println("Error during transfer: " + e.getMessage());
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


    private boolean isValidTransfer(String senderAccount, String receiverAccount, double amount, String password) {
        if (senderAccount == null || senderAccount.isEmpty() ||
            receiverAccount == null || receiverAccount.isEmpty() ||
            password == null || password.isEmpty() ||
            amount <= 0 ||
            senderAccount.equals(receiverAccount)) {
            System.out.println("Invalid transfer details provided.");
            return false;
        }
        return true;
    }
}
