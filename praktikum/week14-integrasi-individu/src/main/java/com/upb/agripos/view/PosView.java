package com.upb.agripos.view;

import com.upb.agripos.controller.PosController;
import com.upb.agripos.model.Product;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class PosView extends VBox {

    public PosView(PosController controller) {

        TableView<Product> table = new TableView<>();

        TableColumn<Product, String> code = new TableColumn<>("Code");
        code.setCellValueFactory(
            c -> new SimpleStringProperty(c.getValue().getCode())
        );

        table.getColumns().add(code);
        table.getItems().addAll(controller.loadProducts());

        Button add = new Button("Tambah ke Keranjang");
        add.setOnAction(e -> {
            Product p = table.getSelectionModel().getSelectedItem();
            if (p != null) {
                controller.addToCart(p);
                System.out.println("TOTAL = " + controller.cartTotal());
            }
        });

        getChildren().addAll(table, add);
    }
}
