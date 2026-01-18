package com.upb.agripos.service;

import com.upb.agripos.model.Cart;
import com.upb.agripos.model.Product;

public class CartService {

    private Cart cart = new Cart();

    public void add(Product p) {
        cart.add(p);
    }

    public double total() {
        return cart.total();
    }
}
