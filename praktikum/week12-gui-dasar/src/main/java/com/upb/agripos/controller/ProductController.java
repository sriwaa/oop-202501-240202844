package com.upb.agripos.controller;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.view.ProductFormView;

public class ProductController {

    private final ProductService service;
    private final ProductFormView view;

    public ProductController(ProductService service, ProductFormView view) {
        this.service = service;
        this.view = view;
        initHandler();
    }

    private void initHandler() {
        view.btnAdd.setOnAction(event -> {
            try {
                Product p = new Product(
                    view.txtCode.getText(),
                    view.txtName.getText(),
                    Double.parseDouble(view.txtPrice.getText()),
                    Integer.parseInt(view.txtStock.getText())
                );

                service.insert(p);

                view.listView.getItems()
                    .add(p.getCode() + " - " + p.getName());

                view.txtCode.clear();
                view.txtName.clear();
                view.txtPrice.clear();
                view.txtStock.clear();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
