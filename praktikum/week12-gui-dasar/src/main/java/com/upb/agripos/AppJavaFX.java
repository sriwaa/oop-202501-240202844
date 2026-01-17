package com.upb.agripos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;

import com.upb.agripos.service.ProductService;
import com.upb.agripos.view.ProductFormView;
import com.upb.agripos.controller.ProductController;

public class AppJavaFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/agripos",
            "postgres",
            "225522"
        );

        ProductFormView view = new ProductFormView();
        ProductService service = new ProductService(conn);
        new ProductController(service, view);

        stage.setTitle("Agri-POS – Kelola Produk");
        stage.setScene(new Scene(view, 400, 500));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
