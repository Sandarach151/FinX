package com.example.finx.Controller;

import com.example.finx.Others.DBHandler;
import com.example.finx.LoginApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SignupController {

    public PasswordField passwordField;
    public TextField usernameText;
    public PasswordField repeatPasswordField;
    @FXML
    private Button signUpBtn;

    @FXML
    void onSignUpBtnClicked(MouseEvent event) throws IOException {
        if(passwordField.getText().equals(repeatPasswordField.getText()) && DBHandler.insertUser(usernameText.getText(), passwordField.getText(), 100000.0, 0, 0, new ArrayList<>())){
            Stage stage = (Stage) signUpBtn.getScene().getWindow();
            LoginApplication app = new LoginApplication();
            app.start(stage);
        }
    }

}
