package com.upb.agripos.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> items = new ArrayList<>();

    public void add(Product p) {
        for (CartItem item : items) {
            if (item.getProduct().getCode().equals(p.getCode())) {
                item.incrementQty();
                return;
            }
        }
        items.add(new CartItem(p, 1));
    }

    public double total() {
        double sum = 0;
        for (CartItem item : items) {
            sum += item.getSubtotal();
        }
        return sum;
    }
}
