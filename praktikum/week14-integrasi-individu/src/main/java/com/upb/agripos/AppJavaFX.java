package com.upb.agripos;

import com.upb.agripos.controller.PosController;
import com.upb.agripos.dao.JdbcProductDAO;
import com.upb.agripos.service.CartService;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.view.PosView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppJavaFX extends Application {

    @Override
    public void start(Stage stage) {

        System.out.println("Hello World, I am Sriwa-240202844");

        JdbcProductDAO productDAO = new JdbcProductDAO();

        ProductService productService = new ProductService(productDAO);
        CartService cartService = new CartService();

        PosController controller = new PosController(productService, cartService);

        PosView view = new PosView(controller);

        stage.setTitle("Agri-POS");
        stage.setScene(new Scene(view, 800, 500));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
