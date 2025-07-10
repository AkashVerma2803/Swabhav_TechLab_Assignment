package com.swabhav.Ecommerce.Model;

public class UPI implements Ecommerce {
	private String upiId;

	public UPI(String upiId) {
		this.upiId = upiId;
	}

	public boolean validate() {
		return upiId.contains("@");
	}

	@Override
	public boolean pay(double amount) {
		if (validate()) {
			System.err.println("Paid " + amount + " using UPI.");
			System.out.println("Thank for using UPI");
			return true;
		} else {
			System.out.println("Invalid payment method ! Please choose UPI");
			return false;
		}
	}

	@Override
	public boolean refund(double amount) {
		System.out.println("Refund " + amount + " to UPI.");
		return false;
	}
}
