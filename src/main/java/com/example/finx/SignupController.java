package com.example.finx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

import java.util.ArrayList;

public class SignupController {

    public PasswordField passwordField;
    public TextField usernameText;
    public PasswordField repeatPasswordField;
    @FXML
    private Button signUpBtn;

    @FXML
    void onSignUpBtnClicked(MouseEvent event) {
        System.out.println(DBHandler.insertUser(usernameText.getText(), passwordField.getText()));
    }

}
