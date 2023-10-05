package com.example.finx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AboutApplication extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("View/about-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("About");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(LoginApplication.class.getResourceAsStream("Images/Logo.png")));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}