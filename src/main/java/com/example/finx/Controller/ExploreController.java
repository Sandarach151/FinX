package com.example.finx.Controller;

import com.example.finx.Model.UserDB;
import com.example.finx.Others.AlphaRequest;
import com.example.finx.Others.DBHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ExploreController {
    @FXML
    private TextField BTCTextField;
    @FXML
    private TextField ETHTextField;
    @FXML
    private TextField BTCHoldings;
    @FXML
    private TextField ETHHoldings;
    @FXML
    private Button BTCDetails;
    @FXML
    private Button ETHDetails;
    @FXML
    private Button goToPortfolioBtn;
    @FXML
    private RadioButton buyRadioBTN;
    @FXML
    private RadioButton sellRadioBtn;
    @FXML
    private TextField BuySellNumUnits;
    @FXML
    private ChoiceBox<String> optionsChoiceBox;
    @FXML
    private Button confirmBtn;
    @FXML
    private Button logOutBtn;
    @FXML
    private Label cashRemainLabel;

    @FXML
    void onConfirmBtnClicked(MouseEvent event) {
        UserDB database = DBHandler.loadUsers();
        if(buyRadioBTN.isSelected()){
            if(optionsChoiceBox.getValue().equals("Bitcoin")){
                database.findUserByUsername(DBHandler.getCurrentUser().getUsername()).buy("BTC", Integer.parseInt(BuySellNumUnits.getText()), Double.parseDouble(BTCTextField.getText()));
            }
            if(optionsChoiceBox.getValue().equals("Ethereum")){
                database.findUserByUsername(DBHandler.getCurrentUser().getUsername()).buy("ETH", Integer.parseInt(BuySellNumUnits.getText()), Double.parseDouble(ETHTextField.getText()));
            }
        }
        if(sellRadioBtn.isSelected()){
            if(optionsChoiceBox.getValue().equals("Bitcoin")){
                database.findUserByUsername(DBHandler.getCurrentUser().getUsername()).sell("BTC", Integer.parseInt(BuySellNumUnits.getText()), Double.parseDouble(BTCTextField.getText()));
            }
            if(optionsChoiceBox.getValue().equals("Ethereum")){
                database.findUserByUsername(DBHandler.getCurrentUser().getUsername()).sell("ETH", Integer.parseInt(BuySellNumUnits.getText()), Double.parseDouble(ETHTextField.getText()));
            }
        }
        DBHandler.printUsers(database);
        BTCHoldings.setText(String.valueOf(DBHandler.getCurrentUser().getBTCHoldings()));
        ETHHoldings.setText(String.valueOf(DBHandler.getCurrentUser().getETHHoldings()));
        cashRemainLabel.setText("$"+DBHandler.getCurrentUser().getBalance());
    }

    public void initialize() throws IOException {
        TextField[] CurrencyTextFields = {BTCTextField, ETHTextField};
        String[] Currencies = {"BTC", "ETH"};
        ObservableList<String> list = FXCollections.observableArrayList("Bitcoin", "Ethereum");
        optionsChoiceBox.setItems(list);
        BTCHoldings.setText(String.valueOf(DBHandler.getCurrentUser().getBTCHoldings()));
        ETHHoldings.setText(String.valueOf(DBHandler.getCurrentUser().getETHHoldings()));
        cashRemainLabel.setText("$"+DBHandler.getCurrentUser().getBalance());
        for(int i=0; i<Currencies.length; i++){
            System.out.println(Currencies[i]);
            double curCurrencyPrice = AlphaRequest.getCurrencyPrice(Currencies[i], "SGD");
            CurrencyTextFields[i].setText(String.valueOf(curCurrencyPrice));
            CurrencyTextFields[i].setEditable(false);
        }
    }
}




















