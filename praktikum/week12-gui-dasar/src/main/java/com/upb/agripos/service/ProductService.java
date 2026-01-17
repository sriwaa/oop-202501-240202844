package com.upb.agripos.service;

import java.sql.Connection;
import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.ProductDAOImpl;
import com.upb.agripos.model.Product;

public class ProductService {

    private final ProductDAO productDAO;

    public ProductService(Connection conn) {
        this.productDAO = new ProductDAOImpl(conn);
    }

    public void insert(Product product) throws Exception {
        productDAO.insert(product);
    }
}
