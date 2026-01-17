package com.upb.agripos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.view.ProductTableView;

public class ProductController {

    private final ProductTableView view;
    private final ProductService service;

    public ProductController(ProductTableView view) {
        this.view = view;
        this.service = new ProductService();

        loadData();
        initAction();
    }

    // UC-02: Lihat Daftar Produk
    public void loadData() {
        ObservableList<Product> products =
                FXCollections.observableArrayList(service.findAll());
        view.table.setItems(products);
    }

    // UC-03: Hapus Produk (Lambda Expression)
    private void initAction() {

        view.btnDelete.setOnAction(e -> {
            Product selected = view.table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                service.delete(selected.getCode());
                loadData(); // reload dari DB
            }
        });

        view.btnAdd.setOnAction(e -> {
            System.out.println("Tambah Produk diklik (opsional lanjut dialog)");
        });
    }
}
