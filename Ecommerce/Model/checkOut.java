package com.swabhav.Ecommerce.Model;

public class checkOut {

    public static void processOrder(Ecommerce gateway, double amount) {
        if (gateway.pay(amount)) {
            System.out.println("Checking Out! Transaction Successful!");
        } else {
            System.out.println("CheckOut Failed! Transaction Failed!");
        }
    }
}
