package com.example.finx.Controller;

import com.example.finx.Others.DBHandler;
import com.example.finx.LoginApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    public PasswordField confirmPasswordField;
    @FXML
    private Button signUpBtn;

    @FXML
    void onSignUpBtnClicked(MouseEvent event) throws IOException {
        if(passwordField.getText().equals(confirmPasswordField.getText())){
            if(passwordField.getText().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")) {
                if (DBHandler.insertUser(usernameText.getText(), passwordField.getText(), 1000000.0, new ArrayList<>())) {
                    Stage stage = (Stage) signUpBtn.getScene().getWindow();
                    LoginApplication app = new LoginApplication();
                    app.start(stage);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Signup Successful. Please Log In.");
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Username already exists");
                    alert.show();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Invalid Password");
                alert.setContentText("At least one upper case English letter, (?=.*?[A-Z])\n" +
                        "At least one lower case English letter, (?=.*?[a-z])\n" +
                        "At least one digit, (?=.*?[0-9])\n" +
                        "At least one special character, (?=.*?[#?!@$%^&*-])\n" +
                        "Minimum eight in length .{8,} (with the anchors)");
                alert.show();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Passwords do not match");
            alert.show();
        }
    }

    @FXML
    void onLoginBtnClicked(MouseEvent event) throws IOException {
        Stage stage = (Stage) signUpBtn.getScene().getWindow();
        LoginApplication app = new LoginApplication();
        app.start(stage);
    }

}
