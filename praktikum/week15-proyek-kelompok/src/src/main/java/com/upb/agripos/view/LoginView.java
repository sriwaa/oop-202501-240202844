package main.java.com.upb.agripos.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LoginView {
    private TextField txtUser;
    private PasswordField txtPass;
    private Button btnLogin;
    private VBox layout;

    public LoginView() {
        txtUser = new TextField(); txtUser.setPromptText("Username");
        txtPass = new PasswordField(); txtPass.setPromptText("Password");
        btnLogin = new Button("LOGIN");
        
        layout = new VBox(15, new Label("=== AGRI-POS LOGIN ==="), txtUser, txtPass, btnLogin);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 50;");
    }

    public Parent asParent() { return layout; }
    public String getUsername() { return txtUser.getText(); }
    public String getPassword() { return txtPass.getText(); }
    public Button getBtnLogin() { return btnLogin; }
}