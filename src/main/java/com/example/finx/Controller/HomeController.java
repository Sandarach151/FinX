package com.example.finx.Controller;

import com.example.finx.ExploreApplication;
import com.example.finx.HistoryApplication;
import com.example.finx.Model.UserDatabase;
import com.example.finx.Others.DBHandler;
import com.example.finx.Others.FinnhubHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeController {

    @FXML
    private ToggleGroup BSstock;

    @FXML
    private Label ExploreBanner;

    @FXML
    private Label aaplChange;

    @FXML
    private Label aaplQty;

    @FXML
    private Label aaplValue;

    @FXML
    private Label accValue;

    @FXML
    private RadioButton bStock;

    @FXML
    private Label btcChange;

    @FXML
    private Label btcQty;

    @FXML
    private Label btcValue;

    @FXML
    private Button confirmPurchase;

    @FXML
    private Label ethChange;

    @FXML
    private Label ethQty;

    @FXML
    private Label ethValue;

    @FXML
    private Button exploreBtn;

    @FXML
    private Label metaChange;

    @FXML
    private Label metaQty;

    @FXML
    private Label metaValue;

    @FXML
    private Label msftChange;

    @FXML
    private Label msftQty;

    @FXML
    private Label msftValue;

    @FXML
    private Label nflxChange;

    @FXML
    private Label nflxQty;

    @FXML
    private Label nflxValue;

    @FXML
    private TextField orderQty;

    @FXML
    private TextField orderSymbol;

    @FXML
    private AnchorPane page;

    @FXML
    private Label remCash;

    @FXML
    private RadioButton sStock;

    @FXML
    private Label sbuxChange;

    @FXML
    private Label sbuxQty;

    @FXML
    private Label sbuxValue;

    @FXML
    private GridPane stockTable;

    @FXML
    private Label todayChange;

    @FXML
    private HBox topHbox;

    @FXML
    private Button transactHistory;

    @FXML
    private Label tslaChange;

    @FXML
    private Label tslaQty;

    @FXML
    private Label tslaValue;

    private String[] symbols;

    private Label[] holdings;

    private Label[] values;

    private Label[] profits;

    private Double[][] stockPricesFinnHub;

    private Thread stockUpdateThread;

    private ExecutorService executorService;

    @FXML
    void onConfirmPurchaseClicked(MouseEvent event) throws IOException {
        ArrayList<String> updatedSymbols = new ArrayList<String>(List.of(symbols));
        updatedSymbols.add("BTC");
        updatedSymbols.add("ETH");
        if(!updatedSymbols.contains(orderSymbol.getText()) || !orderQty.getText().matches("\\d+")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid Symbol or Quantity");
            alert.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Double[] finnhubRes = new Double[3];
                String orderSymbolParsed = "";
                if (orderSymbol.getText().equals("BTC")) {
                    orderSymbolParsed = "BINANCE:BTCUSDT";
                } else if (orderSymbol.getText().equals("ETH")) {
                    orderSymbolParsed = "BINANCE:ETHUSDT";
                } else {
                    orderSymbolParsed = orderSymbol.getText();
                }
                for (int i = 0; i < 8; i++) {
                    if (symbols[i].equals(orderSymbolParsed)) {
                        stockPricesFinnHub[i] = DBHandler.getStockPrice(symbols[i]);
                        finnhubRes = stockPricesFinnHub[i];
                    }
                }
                boolean transactionSuccess = true;
                if (bStock.isSelected()) {
                    UserDatabase db = DBHandler.loadUsers();
                    transactionSuccess = db.find(DBHandler.getCurrentUser().getUsername()).buy(orderSymbolParsed, Integer.parseInt(orderQty.getText()), finnhubRes[0]);
                    DBHandler.printUsers(db);
                }
                if (sStock.isSelected()) {
                    UserDatabase db = DBHandler.loadUsers();
                    transactionSuccess = db.find(DBHandler.getCurrentUser().getUsername()).sell(orderSymbolParsed, Integer.parseInt(orderQty.getText()), finnhubRes[0]);
                    DBHandler.printUsers(db);
                }
                if (!transactionSuccess) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Insufficient Cash or Holdings");
                    alert.show();
                } else {
                    double change = 0.0;
                    double accValueDouble = 0.0;
                    for (int i = 0; i < 8; i++) {
                        String holdingText = String.format("%d", DBHandler.getCurrentUser().getHoldings(symbols[i]));
                        String valueText = String.format("$%.2f", DBHandler.getCurrentUser().getHoldings(symbols[i]) * stockPricesFinnHub[i][0]);
                        String profitText = String.format("$%.2f", stockPricesFinnHub[i][0]);
                        accValueDouble += DBHandler.getCurrentUser().getHoldings(symbols[i]) * stockPricesFinnHub[i][0];
                        change += DBHandler.getCurrentUser().getHoldings(symbols[i]) * stockPricesFinnHub[i][1];
                        holdings[i].setText(holdingText);
                        values[i].setText(valueText);
                        profits[i].setText(profitText);
                        if (DBHandler.getCurrentUser().getHoldings(symbols[i]) == 0) {
                            holdings[i].setStyle("-fx-text-fill: #8b8b8b;");
                            values[i].setStyle("-fx-text-fill: #8b8b8b;");
                        } else {
                            holdings[i].setStyle("-fx-text-fill: white;");
                            values[i].setStyle("-fx-text-fill: white;");
                        }
                        if (stockPricesFinnHub[i][1] > 0) {
                            profits[i].setStyle("-fx-text-fill: #4bb543;");
                        } else {
                            profits[i].setStyle("-fx-text-fill: #b22222;");
                        }
                    }
                    double finalAccValueDouble = accValueDouble + DBHandler.getCurrentUser().getBalance();
                    double finalChange = change;
                    accValue.setText(String.format("$%.2f", finalAccValueDouble));
                    remCash.setText(String.format("$%.2f", DBHandler.getCurrentUser().getBalance()));
                    todayChange.setText(String.format("%.2f", finalChange));
                    if (finalChange > 0) {
                        todayChange.setText("+" + todayChange.getText());
                        todayChange.setStyle("-fx-text-fill: #4bb543;");
                    }
                    if (finalChange < 0) {
                        todayChange.setStyle("-fx-text-fill: #b22222;");
                    }
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Success!");
                    alert.show();
                }
            }
        }
    }

    @FXML
    void onTransactionBtnClicked(MouseEvent event) throws IOException {
        stockUpdateThread.interrupt();
        executorService.shutdown();
        executorService.shutdownNow();
        HistoryApplication app = new HistoryApplication();
        app.start(new Stage());
        Stage primary = (Stage) this.exploreBtn.getScene().getWindow();
        primary.close();
    }

    @FXML
    void onExploreBtnClicked(MouseEvent event) throws IOException {
        stockUpdateThread.interrupt();
        executorService.shutdown();
        executorService.shutdownNow();
        ExploreApplication app = new ExploreApplication();
        app.start(new Stage());
        Stage primary = (Stage) this.exploreBtn.getScene().getWindow();
        primary.close();
    }

    public void initialize() throws IOException {
        symbols = new String[]{"AAPL", "MSFT", "TSLA", "SBUX", "NFLX", "META", "BINANCE:BTCUSDT", "BINANCE:ETHUSDT"};
        holdings = new Label[]{aaplQty, msftQty, tslaQty, sbuxQty, nflxQty, metaQty, btcQty, ethQty};
        values = new Label[]{aaplValue, msftValue, tslaValue, sbuxValue, nflxValue, metaValue, btcValue, ethValue};
        profits = new Label[]{aaplChange, msftChange, tslaChange, sbuxChange, nflxChange, metaChange, btcChange, ethChange};
        stockPricesFinnHub = new Double[8][3];
        bStock.setSelected(true);

        executorService = Executors.newSingleThreadExecutor();

        stockUpdateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while(true) {
                        double change = 0.0;
                        double accValueDouble = 0.0;
                        for (int i = 0; i < 8; i++) {
                            stockPricesFinnHub[i] = DBHandler.getStockPrice(symbols[i]);
                            String holdingText = String.format("%d", DBHandler.getCurrentUser().getHoldings(symbols[i]));
                            String valueText = String.format("$%.2f", DBHandler.getCurrentUser().getHoldings(symbols[i]) * stockPricesFinnHub[i][0]);
                            String profitText = String.format("$%.2f", stockPricesFinnHub[i][0]);
                            accValueDouble += DBHandler.getCurrentUser().getHoldings(symbols[i]) * stockPricesFinnHub[i][0];
                            change += DBHandler.getCurrentUser().getHoldings(symbols[i]) * stockPricesFinnHub[i][1];
                            int finalI = i;
                            Platform.runLater(() -> {
                                holdings[finalI].setText(holdingText);
                                values[finalI].setText(valueText);
                                profits[finalI].setText(profitText);
                                if (DBHandler.getCurrentUser().getHoldings(symbols[finalI]) == 0) {
                                    holdings[finalI].setStyle("-fx-text-fill: #8b8b8b;");
                                    values[finalI].setStyle("-fx-text-fill: #8b8b8b;");
                                } else {
                                    holdings[finalI].setStyle("-fx-text-fill: white;");
                                    values[finalI].setStyle("-fx-text-fill: white;");
                                }
                                if (stockPricesFinnHub[finalI][1] > 0) {
                                    profits[finalI].setStyle("-fx-text-fill: #4bb543;");
                                } else {
                                    profits[finalI].setStyle("-fx-text-fill: #b22222;");
                                }
                            });
                        }
                        double finalAccValueDouble = accValueDouble + DBHandler.getCurrentUser().getBalance();
                        double finalChange = change;
                        Platform.runLater(() -> {
                            accValue.setText(String.format("$%.2f", finalAccValueDouble));
                            remCash.setText(String.format("$%.2f", DBHandler.getCurrentUser().getBalance()));
                            todayChange.setText(String.format("%.2f", finalChange));
                            if (finalChange > 0) {
                                todayChange.setText("+" + todayChange.getText());
                                todayChange.setStyle("-fx-text-fill: #4bb543;");
                            }
                            if (finalChange < 0) {
                                todayChange.setStyle("-fx-text-fill: #b22222;");
                            }
                        });
                        Thread.sleep(15000);
                    }
                } catch (InterruptedException e){
                    System.out.println("Interrupted");
                }
            }
        });
        executorService.execute(stockUpdateThread);
    }

}
