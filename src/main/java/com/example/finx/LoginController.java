package com.example.finx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button signUpBtn;

    @FXML
    void onSignUpBtnClicked() throws IOException {
        Stage stage = (Stage) signUpBtn.getScene().getWindow();
        Application2 app = new Application2();
        app.start(stage);
    }

}
