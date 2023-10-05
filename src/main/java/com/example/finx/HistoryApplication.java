package com.example.finx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HistoryApplication extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("View/history-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Transaction History");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(LoginApplication.class.getResourceAsStream("Images/Logo.png")));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}