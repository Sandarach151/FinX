package com.example.finx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class LoginController {
    public Button loginBtn;
    public PasswordField passwordField;
    public TextField usernameText;
    public Button signUpBtn;

    public void onLoginBtnClicked(MouseEvent mouseEvent) {
        Pair<ArrayList<String>, ArrayList<String>> users = DBHandler.getUsers();
        ArrayList<String> usernameDB = users.getKey();
        ArrayList<String> passwordDB = users.getValue();
        if(usernameDB.indexOf(usernameText.getText())!=-1 && passwordDB.get(usernameDB.indexOf(usernameText.getText())).equals(passwordField.getText())){
            System.out.println("Login Successful");
        }
        else{
            System.out.println("Login Unsuccessful");
        }
    }

    public void onSignUpBtnClicked(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) signUpBtn.getScene().getWindow();
        SignupApplication app = new SignupApplication();
        app.start(stage);
    }
}
