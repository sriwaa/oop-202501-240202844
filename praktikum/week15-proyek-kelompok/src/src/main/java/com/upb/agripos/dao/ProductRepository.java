package main.java.com.upb.agripos.dao;

import main.java.com.upb.agripos.model.Produk;
import java.util.List;

public interface ProductRepository {
    void save(Produk p) throws Exception;
    void delete(String code) throws Exception; 
    Produk findByCode(String code) throws Exception;
    List<Produk> findAll() throws Exception;
    void update(Produk p) throws Exception;
}