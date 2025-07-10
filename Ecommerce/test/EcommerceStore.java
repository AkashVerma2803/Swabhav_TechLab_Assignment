package com.swabhav.Ecommerce.test;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.swabhav.Ecommerce.Model.CreditCard;
import com.swabhav.Ecommerce.Model.NetBanking;
import com.swabhav.Ecommerce.Model.UPI;
import com.swabhav.Ecommerce.Model.checkOut;

public class EcommerceStore {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String cardnumber = "";
		String cvv = "";
		String upiId = "";
		String username = "";
		String password = "";
		int choice = 0;

		boolean flag = true;
		while (flag) {
			try {
				System.out.println("Enter the payment method : ");
				System.out.println("1.Credit card");
				System.out.println("2.UPI");
				System.out.println("3.Net Banking");
				System.out.println("4.Exit");
				choice = scanner.nextInt();
				scanner.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("Invalid Input type ! Select Proper Payment method !");
				scanner.nextLine();
				continue;
			}

			switch (choice) {

// Credit Card 
			case 1:
				try {
					System.out.println("Enter credit card number (16 digit) : ");
					cardnumber = scanner.nextLine().trim();
					while (!cardnumber.matches("\\d{16}")) {
						System.out.println("Invalid card number! Please enter a 16-digit number:");
						cardnumber = scanner.nextLine();
					}

					System.out.println("Enter cvv number (3 digit): ");
					cvv = scanner.nextLine();
					while (!cvv.matches("\\d{3}")) {
						System.out.println("Invalid CVV! Please enter a 3-digit number:");
						cvv = scanner.nextLine();
					}

					CreditCard credit = new CreditCard(cardnumber, cvv);
					double amount = 0;
					boolean validAmount = false;
					while (!validAmount) {
						System.out.println("Enter the amount to be paid : ");
						try {
							amount = scanner.nextDouble();
							scanner.nextLine();
							if (amount <= 0) {
								System.out.println("Amount must be greater than 0!");
							} else {
								validAmount = true;
							}
						} catch (InputMismatchException e) {
							System.out.println("Invalid amount! Please enter a numeric value.");
							scanner.nextLine();
						}
					}

					credit.pay(amount);

					while (true) {
						System.out.println("Do you want refund : ");
						System.out.println("1.Yes");
						System.out.println("2.No");
						try {
							int choose = scanner.nextInt();
							scanner.nextLine();

							if (choose == 1) {
								boolean refundStatus = credit.refund(amount);
								System.out.println(refundStatus ? "Refund Successful" : "Refund Failed");
								break;
							} else if (choose == 2) {
								checkOut.processOrder(credit, amount);
								System.out.println("Generating Credit Card Bill Summary ! : ");
								System.out.println("---------------------");
								System.out.println("! Payment Method : Credit Card !");
								System.out.println("Amount Paid : " + amount);
								System.out.println("Thank You ! ");
								System.out.println("-------------------------");
								break;
							} else {
								System.out.println("Invalid input! Choose 1 or 2.");
							}
						} catch (InputMismatchException e) {
							System.out.println("Invalid input! Please enter numeric value 1 or 2.");
							scanner.nextLine();
						}
					}
				} catch (InputMismatchException e) {
					System.out.println("Invalid input !");
				}
				break;

// UPI 
			case 2:
				try {
					System.out.println("Enter UPI ID  : ");
					upiId = scanner.nextLine().trim();
					while (!upiId.contains("@")) {
						System.out.println("Invalid UPI ID ! use '@'");
						upiId = scanner.nextLine();
					}

					UPI upi = new UPI(upiId);

					double amount = 0;
					boolean validAmount = false;
					while (!validAmount) {
						System.out.println("Enter the amount to be paid : ");
						try {
							amount = scanner.nextDouble();
							scanner.nextLine();
							if (amount <= 0) {
								System.out.println("Amount must be greater than 0!");
							} else {
								validAmount = true;
							}
						} catch (InputMismatchException e) {
							System.out.println("Invalid amount! Please enter a numeric value.");
							scanner.nextLine();
						}
					}

					upi.pay(amount);

					while (true) {
						System.out.println("Do you want refund : ");
						System.out.println("1.Yes");
						System.out.println("2.No");
						try {
							int choose = scanner.nextInt();
							scanner.nextLine();

							if (choose == 1) {
								boolean refundStatus = upi.refund(amount);
								System.out.println(refundStatus ? "Refund Successful" : "Refund Failed");
								break;
							} else if (choose == 2) {
								checkOut.processOrder(upi, amount);
								System.out.println("Generating UPI Bill Summary ! : ");
								System.out.println("---------------------");
								System.out.println("! Payment Method : UPI !");
								System.out.println("Amount Paid : " + amount);
								System.out.println("Thank You ! ");
								System.out.println("-------------------------");
								break;
							} else {
								System.out.println("Invalid input! Choose 1 or 2.");
							}
						} catch (InputMismatchException e) {
							System.out.println("Invalid input! Please enter numeric value 1 or 2.");
							scanner.nextLine();
						}
					}
				} catch (InputMismatchException e) {
					System.out.println("Invalid input !");
				}
				break;

// Net Banking
			case 3:
				try {
					System.out.println("Enter Username  : ");
					username = scanner.nextLine().trim();
					while (!username.matches("[A-Za-z0-9]+")) {
						System.out.println("Invalid username !:");
						username = scanner.nextLine();
					}

					System.out.println("Enter Password (8 digit): ");
					password = scanner.nextLine();
					while (!password.matches("\\d{8}")) {
						System.out.println("Invalid password ! Please enter a 8-digit password:");
						password = scanner.nextLine();
					}

					NetBanking net = new NetBanking(username, password);
					double amount = 0;
					boolean validAmount = false;
					while (!validAmount) {
						System.out.println("Enter the amount to be paid : ");
						try {
							amount = scanner.nextDouble();
							scanner.nextLine();
							if (amount <= 0) {
								System.out.println("Amount must be greater than 0!");
							} else {
								validAmount = true;
							}
						} catch (InputMismatchException e) {
							System.out.println("Invalid amount! Please enter a numeric value.");
							scanner.nextLine();
						}
					}

					net.pay(amount);

					while (true) {
						System.out.println("Do you want refund : ");
						System.out.println("1.Yes");
						System.out.println("2.No");
						try {
							int choose = scanner.nextInt();
							scanner.nextLine();

							if (choose == 1) {
								boolean refundStatus = net.refund(amount);
								System.out.println(refundStatus ? "Refund Successful" : "Refund Failed");
								break;
							} else if (choose == 2) {
								checkOut.processOrder(net, amount);
								System.out.println("Generating Net Banking Bill Summary ! : ");
								System.out.println("---------------------");
								System.out.println("! Payment Method : Net Banking !");
								System.out.println("Amount Paid : " + amount);
								System.out.println("Thank You ! ");
								System.out.println("-------------------------");
								break;
							} else {
								System.out.println("Invalid input! Choose 1 or 2.");
							}
						} catch (InputMismatchException e) {
							System.out.println("Invalid input! Please enter numeric value 1 or 2.");
							scanner.nextLine();
						}
					}
				} catch (InputMismatchException e) {
					System.out.println("Invalid input !");
				}
				break;

			case 4:
				flag = false;
				System.out.println("Exit ! Thank you for using !");
				break;

			default:
				System.out.println("Invalid choice ! Choose between (1-4)");
			}
		}
		scanner.close();
	}
}
