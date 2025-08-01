package com.swabhav.transaction.model;

import java.sql.*;
import java.util.Scanner;

public class Check_Transaction_History {

    public void showHistory(String accountNumber, String password) {
        try (Connection con = DButility.getConnection()) {
            if (!Account.isAccountNumberExists(accountNumber)) {
                System.err.println("Account does not exist.");
                return;
            }

            Scanner scanner = new Scanner(System.in);
            System.out.println("Do you want to view:");
            System.out.println("1. Full Transaction History");
            System.out.println("2. Mini Statement (Last 2 transactions)");
            System.out.print("Enter your choice (1 or 2): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            String query;
            if (choice == 1) {
                query = "SELECT transaction_type, amount, from_account, to_account, description, transaction_date " +
                        "FROM transactions WHERE account_number = ? ORDER BY transaction_date DESC";
            } else if (choice == 2) {
                query = "SELECT transaction_type, amount, from_account, to_account, description, transaction_date " +
                        "FROM transactions WHERE account_number = ? ORDER BY transaction_date DESC LIMIT 2";
            } else {
                System.err.println("Invalid choice. Please select 1 or 2.");
                return;
            }

            try (PreparedStatement stmt = con.prepareStatement(query)) {
                stmt.setString(1, accountNumber);
                ResultSet rs = stmt.executeQuery();

                if (!rs.isBeforeFirst()) {
                    System.out.println("No transactions found for this account.");
                    return;
                }

                System.out.printf("%-20s %-15s %-20s %-20s %-30s %-20s%n", 
                                  "Transaction Type", "Amount", "From Account", "To Account", "Description", "Transaction Date");

                while (rs.next()) {
                    String transactionType = rs.getString("transaction_type");
                    double amount = rs.getDouble("amount");
                    String fromAccount = rs.getString("from_account");
                    String toAccount = rs.getString("to_account");
                    String description = rs.getString("description");
                    Timestamp transactionDate = rs.getTimestamp("transaction_date");

                    System.out.printf("%-20s ₹%-14.2f %-20s %-20s %-30s %-20s%n", 
                                      transactionType, amount, 
                                      (fromAccount != null ? fromAccount : "N/A"), 
                                      (toAccount != null ? toAccount : "N/A"), 
                                      description, 
                                      transactionDate.toString());
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching transaction history: " + e.getMessage());
        }
    }

    
}
