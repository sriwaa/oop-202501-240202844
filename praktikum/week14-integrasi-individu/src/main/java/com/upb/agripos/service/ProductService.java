package com.upb.agripos.service;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.model.Product;
import java.util.List;

public class ProductService {

    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> getAll() {
        return productDAO.findAll();
    }

    public void insert(String code, String name, double price, int stock) {
        if (code == null || code.isEmpty())
            throw new InvalidProductException("Kode produk kosong");

        if (price <= 0 || stock < 0)
            throw new InvalidProductException("Harga / stok tidak valid");

        Product p = new Product(code, name, price, stock);
        productDAO.insert(p);
    }

    public void delete(String code) {
        productDAO.delete(code);
    }
}
