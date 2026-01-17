package com.upb.agripos.service;

import com.upb.agripos.dao.*;
import com.upb.agripos.model.Product;
import java.util.List;

public class ProductService {

    private ProductDAO dao = new ProductDAOImpl();

    public List<Product> findAll() {
        return dao.findAll();
    }

    public void save(Product p) {
        dao.save(p);
    }

    public void delete(String code) {
        dao.delete(code);
    }
}
