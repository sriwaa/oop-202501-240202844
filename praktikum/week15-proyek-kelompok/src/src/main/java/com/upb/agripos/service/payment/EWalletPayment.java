package main.java.com.upb.agripos.service.payment;

public class EWalletPayment implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Connecting to QRIS API... Paid: " + amount);
        return true; 
    }
    @Override
    public String getMethodName() { return "E-WALLET"; }
}