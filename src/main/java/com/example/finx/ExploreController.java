package com.example.finx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;

public class ExploreController {
    @FXML
    private TextField BNBTextField;

    @FXML
    private TextField BTCTextField;

    @FXML
    private TextField ETHTextField;

    @FXML
    private TextField SOLTextField;

    @FXML
    private Label aboutLabel;

    @FXML
    private Label exploreLabel;

    @FXML
    private Label portfolioLabel;

    @FXML
    private GridPane stockGridPane;

    @FXML
    private Label usernameLabel;

    public void initialize() throws IOException {
        TextField[] CurrencyTextFields = {BTCTextField, ETHTextField, SOLTextField, BNBTextField};
        String[] Currencies = {"BTC", "ETH", "SOL", "BNB"};
        for(int i=0; i<Currencies.length; i++){
            System.out.println(Currencies[i]);
            double curCurrencyPrice = AlphaRequest.getCurrencyPrice(Currencies[i], "SGD");
            CurrencyTextFields[i].setText(String.valueOf(curCurrencyPrice));
            CurrencyTextFields[i].setEditable(false);
        }
    }
}
