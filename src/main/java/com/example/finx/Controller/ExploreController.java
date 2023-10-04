package com.example.finx.Controller;

import com.example.finx.AboutApplication;
import com.example.finx.HomeApplication;
import com.example.finx.Model.NewsArticle;
import com.example.finx.Others.DBHandler;
import com.example.finx.Others.FinnhubHandler;
import com.example.finx.StockViewApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExploreController {

    @FXML
    private Label aaplPercent;

    @FXML
    private Label applNum;

    @FXML
    private Label applPrice;

    @FXML
    private Label metaNum;

    @FXML
    private Label metaPercent;

    @FXML
    private Label metaPrice;

    @FXML
    private Label msftNum;

    @FXML
    private Label msftPercent;

    @FXML
    private Label msftPrice;

    @FXML
    private Label nflxNum;

    @FXML
    private Label nflxPercent;

    @FXML
    private Label nflxPrice;

    @FXML
    private Button portfolioBtn;

    @FXML
    private Label sbuxNum;

    @FXML
    private Label sbuxPercent;

    @FXML
    private Label sbuxPrice;

    @FXML
    private Label tslaNum;

    @FXML
    private Label tslaPercent;

    @FXML
    private Label tslaPrice;

    @FXML
    private Label btcNum;

    @FXML
    private Label btcPercent;

    @FXML
    private Label btcPrice;

    @FXML
    private Label ethNum;

    @FXML
    private Label ethPercent;

    @FXML
    private Label ethPrice;

    @FXML
    private Label headlineLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Hyperlink newsURL;

    private Thread stockUpdateThread;

    private ExecutorService executorService;

    @FXML
    void onPortfolioBtnClicked(MouseEvent event) throws IOException {
        stockUpdateThread.interrupt();
        executorService.shutdown();
        executorService.shutdownNow();
        HomeApplication app = new HomeApplication();
        app.start(new Stage());
        Stage primary = (Stage) this.portfolioBtn.getScene().getWindow();
        primary.close();
    }

    @FXML
    void showAbout(MouseEvent event) throws IOException {
        AboutApplication app = new AboutApplication();
        app.start(new Stage());
    }

    @FXML
    void showAAPLview(MouseEvent event) throws IOException {
        stockUpdateThread.interrupt();
        executorService.shutdown();
        executorService.shutdownNow();
        DBHandler.setCurrentStock("AAPL");
        StockViewApplication app = new StockViewApplication();
        app.start(new Stage());
        Stage primary = (Stage) this.portfolioBtn.getScene().getWindow();
        primary.close();
    }

    @FXML
    void showMSFTview(MouseEvent event) throws IOException {
        stockUpdateThread.interrupt();
        executorService.shutdown();
        executorService.shutdownNow();
        DBHandler.setCurrentStock("MSFT");
        StockViewApplication app = new StockViewApplication();
        app.start(new Stage());
        Stage primary = (Stage) this.portfolioBtn.getScene().getWindow();
        primary.close();
    }

    @FXML
    void showTSLAview(MouseEvent event) throws IOException {
        stockUpdateThread.interrupt();
        executorService.shutdown();
        executorService.shutdownNow();
        DBHandler.setCurrentStock("TSLA");
        StockViewApplication app = new StockViewApplication();
        app.start(new Stage());
        Stage primary = (Stage) this.portfolioBtn.getScene().getWindow();
        primary.close();
    }

    @FXML
    void showNFLXview(MouseEvent event) throws IOException {
        stockUpdateThread.interrupt();
        executorService.shutdown();
        executorService.shutdownNow();
        DBHandler.setCurrentStock("NFLX");
        StockViewApplication app = new StockViewApplication();
        app.start(new Stage());
        Stage primary = (Stage) this.portfolioBtn.getScene().getWindow();
        primary.close();
    }

    @FXML
    void showSBUXview(MouseEvent event) throws IOException {
        stockUpdateThread.interrupt();
        executorService.shutdown();
        executorService.shutdownNow();
        DBHandler.setCurrentStock("SBUX");
        StockViewApplication app = new StockViewApplication();
        app.start(new Stage());
        Stage primary = (Stage) this.portfolioBtn.getScene().getWindow();
        primary.close();
    }

    @FXML
    void showMETAview(MouseEvent event) throws IOException {
        stockUpdateThread.interrupt();
        executorService.shutdown();
        executorService.shutdownNow();
        DBHandler.setCurrentStock("META");
        StockViewApplication app = new StockViewApplication();
        app.start(new Stage());
        Stage primary = (Stage) this.portfolioBtn.getScene().getWindow();
        primary.close();
    }

    public void initialize() throws IOException {
        String[] symbols = {"AAPL", "MSFT", "TSLA", "SBUX", "NFLX", "META", "BINANCE:BTCUSDT", "BINANCE:ETHUSDT"};
        Label[] percents = {aaplPercent, msftPercent, tslaPercent, sbuxPercent, nflxPercent, metaPercent, btcPercent, ethPercent};
        Label[] nums = {applNum, msftNum, tslaNum, sbuxNum, nflxNum, metaNum, btcNum, ethNum};
        Label[] prices = {applPrice, msftPrice, tslaPrice, sbuxPrice, nflxPrice, metaPrice, btcPrice, ethPrice};

        executorService = Executors.newSingleThreadExecutor();

        stockUpdateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while(true) {
                        for (int i = 0; i < 8; i++) {
                            Double[] curResult = DBHandler.getStockPrice(symbols[i]);
                            String percentText = String.format("%.2f%%", curResult[2]);
                            String numText = String.format("%.2f", curResult[1]);
                            String priceText = String.format("%.2f", curResult[0]);
                            int finalI = i;
                            Platform.runLater(() -> {
                                // Update UI components using Platform.runLater()
                                percents[finalI].setText(curResult[2] > 0 ? "+" + percentText : percentText);
                                percents[finalI].setStyle(curResult[2] > 0 ? "-fx-text-fill: #4bb543;" : "-fx-text-fill: #b22222;");
                                nums[finalI].setText(curResult[2] > 0 ? "+" + numText : numText);
                                nums[finalI].setStyle(curResult[2] > 0 ? "-fx-text-fill: #4bb543;" : "-fx-text-fill: #b22222;");
                                prices[finalI].setText(priceText);
                            });
                        }
                        NewsArticle news = FinnhubHandler.getRandNews();
                        System.out.println(news);
                        Platform.runLater(() -> {
                            // Update UI components using Platform.runLater()
                            headlineLabel.setText(news.getHeadline());
                            dateLabel.setText(Date.from(news.getPublishTime()).toString());
                            newsURL.setText(news.getUrl());
                            newsURL.setVisited(false);
                        });
                        Thread.sleep(15000);
                    }
                } catch(InterruptedException e){
                    System.out.println("Interrupted");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        executorService.execute(stockUpdateThread);
    }
}