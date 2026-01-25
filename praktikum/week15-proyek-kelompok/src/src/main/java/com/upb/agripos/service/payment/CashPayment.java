package main.java.com.upb.agripos.service.payment;

public class CashPayment implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing Cash Payment: " + amount);
        return true; // Selalu sukses untuk simulasi
    }
    @Override
    public String getMethodName() { return "CASH"; }
}