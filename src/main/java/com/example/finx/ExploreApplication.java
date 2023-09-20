package com.example.finx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ExploreApplication extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        String[] currencies = {"BTC", "CNY", "SGD", "ETH"};
        GridPane pane = new GridPane();
        pane.setPrefWidth(400);
        pane.setPrefHeight(600);
        for(int i=0; i<currencies.length; i++){
            Label curCurrency = new Label(currencies[i]+AlphaRequest.getCurrencyPrice(currencies[i], "USD"));
            pane.add(curCurrency, 0, i);
        }
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Hello!");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}