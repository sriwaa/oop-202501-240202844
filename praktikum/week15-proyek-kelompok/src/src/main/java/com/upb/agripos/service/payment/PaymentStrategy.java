package main.java.com.upb.agripos.service.payment;

public interface PaymentStrategy {
    boolean processPayment(double amount);
    String getMethodName();
}