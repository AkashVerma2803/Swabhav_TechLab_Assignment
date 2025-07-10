package com.swabhav.Ecommerce.Model;

public class NetBanking implements Ecommerce {

	private String username;
	private String password;

	public NetBanking(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public boolean validate() {
		return username.length() > 3 && password.length() >= 6;
	}

	@Override
	public boolean pay(double amount) {
		if (validate()) {
			System.err.println("Paid " + amount + " using Net Banking.");
			System.out.println("Thank for using Net banking ");
			return true;
		} else {
			//System.out.println("Invalid payment method ! Please choose Net Banking");
			return false;
		}
	}

	@Override
	public boolean refund(double amount) {
		System.out.println("Refund " + amount + " to Net Banking");
		return true;
	}
}
