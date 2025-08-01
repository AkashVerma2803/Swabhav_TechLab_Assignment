package com.swabhav.transaction.model;

import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;

public class Loan {
    private String accountNumber;
    private double loanAmount;
    private double interestRate;
    private int loanTenure;
    private double emi;
    private double totalAmountToRepay;
    private String status;

    public Loan() {
        this.status = "ACTIVE";
    }

    public void applyLoan(String accountNumber, double loanAmount, double interestRate, int loanTenure) {
        
        if (loanAmount <= 0) {
            System.out.println("❌ Loan amount must be greater than zero.");
            return;
        }
        if (interestRate <= 0) {
            System.out.println("❌ Interest rate must be greater than zero.");
            return;
        }
        if (loanTenure <= 0) {
            System.out.println("❌ Loan tenure must be greater than zero months.");
            return;
        }

        this.accountNumber = accountNumber;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.loanTenure = loanTenure;

        this.emi = calculateEMI(loanAmount, interestRate, loanTenure);
        this.totalAmountToRepay = emi * loanTenure;

        saveLoanDetailsToDatabase();
    }

    private double calculateEMI(double loanAmount, double interestRate, int loanTenure) {
        double monthlyInterestRate = (interestRate / 100) / 12;
        double emi = (loanAmount * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, loanTenure)) /
                     (Math.pow(1 + monthlyInterestRate, loanTenure) - 1);
        return emi;
    }

    private void saveLoanDetailsToDatabase() {
        try (Connection con = DButility.getConnection()) {
            String updateBalanceQuery = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
            try (PreparedStatement stmt = con.prepareStatement(updateBalanceQuery)) {
                stmt.setDouble(1, loanAmount);
                stmt.setString(2, accountNumber);
                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated > 0) {
                    String insertLoanDetailsQuery = "INSERT INTO loans (account_number, loan_amount, interest_rate, loan_tenure, emi, total_amount_to_repay, status) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement loanStmt = con.prepareStatement(insertLoanDetailsQuery)) {
                        loanStmt.setString(1, accountNumber);
                        loanStmt.setBigDecimal(2, BigDecimal.valueOf(loanAmount));
                        loanStmt.setBigDecimal(3, BigDecimal.valueOf(interestRate));
                        loanStmt.setInt(4, loanTenure);
                        loanStmt.setBigDecimal(5, BigDecimal.valueOf(emi));
                        loanStmt.setBigDecimal(6, BigDecimal.valueOf(totalAmountToRepay));
                        loanStmt.setString(7, status);
                        loanStmt.executeUpdate();
                    }

                    System.out.println("Loan Application Successful!");
                    System.out.println("Loan Amount: ₹" + loanAmount);
                    System.out.println("Interest Rate: " + interestRate + "%");
                    System.out.println("Loan Tenure: " + loanTenure + " months");
                    System.out.println("EMI: ₹" + emi);
                    System.out.println("Total Repayment Amount: ₹" + totalAmountToRepay);
                    System.out.println("Loan amount of ₹" + loanAmount + " has been added to your account.");
                } else {
                    System.out.println("❌ Failed to update balance after loan approval.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error during loan application: " + e.getMessage());
        }
    }

    public void viewLoanDetails(String accountNumber) {
        try (Connection con = DButility.getConnection()) {
            String selectLoanQuery = "SELECT * FROM loans WHERE account_number = ? AND status = 'ACTIVE'";
            try (PreparedStatement stmt = con.prepareStatement(selectLoanQuery)) {
                stmt.setString(1, accountNumber);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        DecimalFormat df = new DecimalFormat("##.##");
                        System.out.println("Loan ID: " + rs.getInt("loan_id"));
                        System.out.println("Loan Amount: ₹" + df.format(rs.getDouble("loan_amount")));
                        System.out.println("Interest Rate: " + df.format(rs.getDouble("interest_rate")) + "%");
                        System.out.println("Loan Tenure: " + rs.getInt("loan_tenure") + " months");
                        System.out.println("EMI: ₹" + df.format(rs.getDouble("emi")));
                        System.out.println("Total Repayment Amount: ₹" + df.format(rs.getDouble("total_amount_to_repay")));
                        System.out.println("Loan Status: " + rs.getString("status"));
                    } else {
                        System.out.println("❌ No active loan found for this account.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error during loan detail retrieval: " + e.getMessage());
        }
    }

    public void repayLoan(String accountNumber, double repaymentAmount) {
        if (repaymentAmount <= 0) {
            System.out.println("❌ Repayment amount must be greater than zero.");
            return;
        }

        try (Connection con = DButility.getConnection()) {
            String selectLoanQuery = "SELECT loan_amount FROM loans WHERE account_number = ? AND status = 'ACTIVE'";
            try (PreparedStatement stmt = con.prepareStatement(selectLoanQuery)) {
                stmt.setString(1, accountNumber);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        double currentLoanAmount = rs.getDouble("loan_amount");

                        if (repaymentAmount > currentLoanAmount) {
                            System.out.println("❌ You can only repay the loan amount you received: ₹" + currentLoanAmount);
                            return;
                        }

                        String updateBalanceQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
                        try (PreparedStatement balanceStmt = con.prepareStatement(updateBalanceQuery)) {
                            balanceStmt.setDouble(1, repaymentAmount);
                            balanceStmt.setString(2, accountNumber);
                            int rowsUpdated = balanceStmt.executeUpdate();

                            if (rowsUpdated > 0) {
                                double remainingLoanAmount = currentLoanAmount - repaymentAmount;

                                String updateLoanAmountQuery = "UPDATE loans SET loan_amount = ?, status = ? WHERE account_number = ? AND status = 'ACTIVE'";
                                try (PreparedStatement loanStmt = con.prepareStatement(updateLoanAmountQuery)) {
                                    loanStmt.setDouble(1, remainingLoanAmount);
                                    loanStmt.setString(2, remainingLoanAmount == 0 ? "CLOSED" : "ACTIVE");
                                    loanStmt.setString(3, accountNumber);
                                    loanStmt.executeUpdate();
                                }

                                System.out.println("Repayment of ₹" + repaymentAmount + " successful.");
                                System.out.println("Remaining Loan Amount: ₹" + remainingLoanAmount);

                                if (remainingLoanAmount == 0) {
                                    System.out.println("✅ Loan fully repaid. Your loan status is now 'CLOSED'.");
                                }
                            } else {
                                System.out.println("❌ Failed to deduct repayment from balance.");
                            }
                        }
                    } else {
                        System.out.println("❌ No active loan found for this account.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error during loan repayment: " + e.getMessage());
        }
    }
}
