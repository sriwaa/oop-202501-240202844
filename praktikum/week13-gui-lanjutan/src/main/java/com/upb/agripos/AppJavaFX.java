package com.upb.agripos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.upb.agripos.view.ProductTableView;
import com.upb.agripos.controller.ProductController;

public class AppJavaFX extends Application {

    @Override
    public void start(Stage stage) {

        ProductTableView view = new ProductTableView();
        new ProductController(view); 
        Scene scene = new Scene(view, 800, 500);
        stage.setTitle("Agri-POS - GUI Lanjutan");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
