package com.swabhav.Ecommerce.Model;

public class CreditCard implements Ecommerce {
	private String cardnumber;
	private String cvv;

	public CreditCard(String cardnumber, String cvv) {
		this.cardnumber = cardnumber;
		this.cvv = cvv;
	}

	public boolean validate() {
		return cardnumber.length() == 16 && cvv.length() == 3;
	}

	@Override
	public boolean pay(double amount) {
		if (validate()) {
			System.err.println("Paid " + amount + " using Credit card.");
			System.out.println("Thank for using Credit card ");
			return true;
		} else {
			//System.out.println("Invalid payment method ! Please choose Credit Card");
			return false;
		}
	}

	@Override
	public boolean refund(double amount) {
		System.out.println("Refund " + amount + " to Credit Card.");
		return true;
	}

}
