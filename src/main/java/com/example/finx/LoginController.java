package com.example.finx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button signUpBtn;

    @FXML
    protected void onSignUpBtnClicked() throws IOException {
        Stage stage = (Stage) signUpBtn.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 335, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

}
