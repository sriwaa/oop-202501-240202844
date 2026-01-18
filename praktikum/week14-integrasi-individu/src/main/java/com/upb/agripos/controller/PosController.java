package com.upb.agripos.controller;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.CartService;
import com.upb.agripos.service.ProductService;

import java.util.List;

public class PosController {

    private ProductService productService;
    private CartService cartService;

    public PosController(ProductService ps, CartService cs) {
        this.productService = ps;
        this.cartService = cs;
    }

    public List<Product> loadProducts() {
        return productService.getAll();
    }

    public void addToCart(Product p) {
        cartService.add(p);
    }

    public double cartTotal() {
        return cartService.total();
    }
}
