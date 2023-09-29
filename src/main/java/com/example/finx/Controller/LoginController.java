package com.example.finx.Controller;

import com.example.finx.HomeApplication;
import com.example.finx.Others.DBHandler;
import com.example.finx.SignupApplication;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.example.finx.Model.*;

import java.io.*;

public class LoginController {
    public Button loginBtn;
    public PasswordField passwordField;
    public TextField usernameText;
    public Button signUpBtn;

    public void onLoginBtnClicked(MouseEvent mouseEvent) throws IOException {
        UserDatabase database = DBHandler.loadUsers();
        if(database.find(usernameText.getText())!=null && database.find(usernameText.getText()).getPassword().equals(passwordField.getText())){
            System.out.println("Login Successful");
            DBHandler.setCurrentUser(usernameText.getText());
            HomeApplication app = new HomeApplication();
            app.start(new Stage());
            Stage primary = (Stage) this.signUpBtn.getScene().getWindow();
            primary.close();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Username or Password");
            alert.show();
        }
    }

    public void onSignUpBtnClicked(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) signUpBtn.getScene().getWindow();
        SignupApplication app = new SignupApplication();
        app.start(stage);
    }
}
