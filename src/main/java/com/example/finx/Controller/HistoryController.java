package com.example.finx.Controller;

import com.example.finx.Others.DBHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import com.example.finx.Model.*;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class HistoryController {

    @FXML
    private Label ExploreBanner;

    @FXML
    private Button homeBtn;

    @FXML
    private AnchorPane page;

    @FXML
    private ScrollPane stockScrollPane;

    @FXML
    private GridPane stockTable;

    @FXML
    private HBox topHbox;

    @FXML
    void onHomeBtnClicked(MouseEvent event) {

    }

    public void initialize() {
        ArrayList<Stock> transactions = DBHandler.getCurrentUser().getStocks();
        for (int i = 0; i < transactions.size(); i++) {
            Stock stock = transactions.get(i);
            Label symbol = new Label(stock.getSymbol());
            symbol.setFont(new Font(15));
            symbol.setPadding(new Insets(0, 0, 0, 15));
            Label quantity = new Label(stock.getAmt() + "");
            quantity.setFont(new Font(15));
            quantity.setPadding(new Insets(0, 0, 0, 15));
            Label bs = new Label(stock.getBS() + "");
            bs.setFont(new Font(15));
            bs.setPadding(new Insets(0, 0, 0, 15));
            Label price = new Label(String.format("$%.2f", stock.getPrice()));
            price.setFont(new Font(15));
            price.setPadding(new Insets(0, 0, 0, 15));
            Label date = new Label(stock.getTransactionDate().format(DateTimeFormatter));
            date.setFont(new Font(15));
            date.setPadding(new Insets(0, 0, 0, 15));
            stockTable.getRowConstraints().add(new RowConstraints(40));
            stockTable.add(symbol, 0, i+1);
            stockTable.add(quantity, 1, i+1);
            stockTable.add(bs, 2, i+1);
            stockTable.add(price, 3, i+1);
            stockTable.add(date, 4, i+1);
        }
    }

}
