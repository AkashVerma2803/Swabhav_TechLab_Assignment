package com.swabhav.transaction.test;

import java.sql.Connection;
import java.util.Scanner;

import com.swabhav.transaction.model.Account;
import com.swabhav.transaction.model.CheckBalance;
import com.swabhav.transaction.model.Check_Transaction_History;
import com.swabhav.transaction.model.Deposit;
import com.swabhav.transaction.model.DButility;
import com.swabhav.transaction.model.Loan;
import com.swabhav.transaction.model.Transfer;
import com.swabhav.transaction.model.Withdraw;

public class Transaction_Test {

    public static void testDatabaseConnection() {
        try {
            Connection con = DButility.getConnection();
            System.out.println("Connection Created Successfully");
            con.close();
        } catch (Exception e) {
            System.out.println("Failed to connect database");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            try {
                int options = 0;
                while (true) {
                    System.out.println("1. Login");
                    System.out.println("2. Add new account");
                    System.out.println("3. Exit");
                    System.out.print("Enter your choice (1-3): ");
                    
                    String input = scanner.nextLine().trim();
                    try {
                        options = Integer.parseInt(input);
                        if (options >= 1 && options <= 3) {
                            break;
                        } else {
                            System.err.println("❌ Invalid choice. Please enter a number between 1 and 3.");
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("❌ Invalid input. Please enter a valid number (1, 2, or 3).");
                    }
                }

                if (options == 1) {
                    System.out.println("Enter your account number:");
                    String loggedInAccount = scanner.nextLine().trim();

                    System.out.println("Enter your password:");
                    String loggedInPassword = scanner.nextLine().trim();

                    CheckBalance check = new CheckBalance();
                    if (!check.isAccountValid(loggedInAccount, loggedInPassword)) {
                        System.err.println("❌ Invalid account number or password. Try again.");
                        continue;
                    }

                    System.out.println("✅ Login successful! Welcome!");

                    boolean sessionActive = true;
                    while (sessionActive) {
                        System.out.println("\nChoose an action:");
                        System.out.println("1. Check Balance");
                        System.out.println("2. Transfer Money");
                        System.out.println("3. Deposit Money");
                        System.out.println("4. Withdraw Money");
                        System.out.println("5. Check Transaction History");
                        System.out.println("6. Apply for Loan");
                        System.out.println("7. View Loan Details");
                        System.out.println("8. Repay Loan");
                        System.out.println("9. Logout");

                        if (!scanner.hasNextInt()) {
                            System.err.println("Invalid input. Please enter a number between 1 and 9.");
                            scanner.nextLine();
                            continue;
                        }
                        int loginChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        if (loginChoice < 1 || loginChoice > 9) {
                            System.err.println("Invalid choice. Please select between 1 and 9.");
                            continue;
                        }

                        Loan loan = new Loan();  // Instantiate Loan class

                        switch (loginChoice) {
                            case 1:
                                new CheckBalance().checkBalance(loggedInAccount, loggedInPassword);
                                break;
                            case 2:
                                System.out.println("Enter receiver's account number:");
                                String receiverAccount = scanner.nextLine().trim();

                                if (receiverAccount.equals(loggedInAccount)) {
                                    System.err.println("No! No! Self-transfer is not allowed.");
                                    break;
                                }

                                if (!Account.isAccountNumberExists(loggedInAccount)) {
                                    System.err.println("Sender account does not exist.");
                                    break;
                                }

                                if (!Account.isAccountNumberExists(receiverAccount)) {
                                    System.err.println("Receiver account does not exist.");
                                    break;
                                }

                                System.out.println("Please re-enter your password for verification:");
                                String passwordCheck = scanner.nextLine().trim();

                                if (!Account.validateAccountPassword(loggedInAccount, passwordCheck)) {
                                    System.err.println("Invalid password for sender account.");
                                    break;
                                }

                                System.out.println("Enter amount to transfer:");
                                if (!scanner.hasNextDouble()) {
                                    System.err.println("Invalid amount. Please enter a number.");
                                    scanner.nextLine();
                                    break;
                                }
                                double amount = scanner.nextDouble();
                                scanner.nextLine();

                                Transfer transfer = new Transfer();
                                transfer.transfer(loggedInAccount, receiverAccount, amount, passwordCheck);
                                break;
                            case 3:
                                System.out.println("Enter deposit amount:");
                                if (!scanner.hasNextDouble()) {
                                    System.err.println("Invalid amount. Please enter a number.");
                                    scanner.nextLine();
                                    break;
                                }
                                double depositAmount = scanner.nextDouble();
                                scanner.nextLine();

                                Deposit deposit = new Deposit();
                                deposit.deposit(loggedInAccount, depositAmount, loggedInPassword);
                                break;
                            case 4:
                                System.out.println("Enter withdrawal amount:");
                                if (!scanner.hasNextDouble()) {
                                    System.err.println("Invalid amount. Please enter a number.");
                                    scanner.nextLine();
                                    break;
                                }
                                double withdrawAmount = scanner.nextDouble();
                                scanner.nextLine();

                                Withdraw withdraw = new Withdraw();
                                withdraw.withdraw(loggedInAccount, withdrawAmount, loggedInPassword);
                                break;
                            case 5:
                                Check_Transaction_History history = new Check_Transaction_History();
                                history.showHistory(loggedInAccount, loggedInPassword);
                                break;
                            case 6:
                                System.out.println("Enter loan amount:");
                                double loanAmount = scanner.nextDouble();
                                System.out.println("Enter interest rate (in %):");
                                double interestRate = scanner.nextDouble();
                                System.out.println("Enter loan tenure (in months):");
                                int loanTenure = scanner.nextInt();
                                scanner.nextLine(); // Consume newline

                                loan.applyLoan(loggedInAccount, loanAmount, interestRate, loanTenure);
                                break;
                            case 7:
                                loan.viewLoanDetails(loggedInAccount);
                                break;
                            case 8:
                                
                                System.out.println("Enter repayment amount:");
                                if (!scanner.hasNextDouble()) {
                                    System.err.println("Invalid amount. Please enter a number.");
                                    scanner.nextLine();  // Consume invalid input
                                    break;
                                }
                                double repaymentAmount = scanner.nextDouble();
                                scanner.nextLine(); // Consume newline

                                // Call repayLoan method from Loan class
                                loan.repayLoan(loggedInAccount, repaymentAmount);
                                break;

                            case 9:
                                sessionActive = false;
                                System.out.println("Logged out successfully.");
                                break;
                        }
                    }
                }

                if (options == 2) {
                    System.out.println("\n---------Create New Account------------\n");

                    System.out.println("Enter the username:");
                    String username = scanner.nextLine().trim();
                    if (username.isEmpty()) {
                        System.err.println("❌ Username cannot be empty.");
                        continue;
                    }

                    System.out.println("Enter the account number (8 to 12 digits):");
                    String account_number = scanner.nextLine().trim();

                    if (!account_number.matches("\\d{8,12}")) {
                        System.err.println("❌ Invalid account number format. Must be 8 to 12 digits.");
                        continue;
                    }

                    System.out.println("Enter the password (min 4 characters):");
                    String password = scanner.nextLine().trim();
                    if (password.length() < 4) {
                        System.err.println("❌ Password must be at least 4 characters long.");
                        continue;
                    }

                    System.out.println("Enter initial balance (0 - 100000):");
                    if (!scanner.hasNextDouble()) {
                        System.err.println("❌ Invalid balance. Please enter a numeric value.");
                        scanner.nextLine();
                        continue;
                    }
                    double balance = scanner.nextDouble();
                    scanner.nextLine();

                    if (balance < 0 || balance > 100000) {
                        System.err.println("❌ Balance must be between 0 and 100000.");
                        continue;
                    }

                    Account accounts = new Account(username, account_number, password, balance);

                    if (accounts.canCreateAccount()) {
                        if (accounts.saveToDatabase()) {
                            System.out.println("✅ Account Created Successfully!");
                            System.out.println("Username: " + username);
                            System.out.println("Account Number: " + account_number);
                            System.out.println("Initial Balance: ₹" + balance);
                        } else {
                            System.err.println("❌ Failed to create account due to technical issues.");
                        }
                    } else {
                        System.err.println("❌ Account creation failed. The account number may already exist.");
                    }
                }

                if (options == 3) {
                    flag = false;
                    System.err.println("Thanks for using the banking application!!");
                }

            } catch (Exception e) {
                System.err.println("Invalid choice!!");
            }
        }

        scanner.close();
    }
}