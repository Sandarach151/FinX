package com.example.finx.Controller;

import com.example.finx.Others.FinnhubHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Arrays;

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
    private ImageView newsImage;

    @FXML
    private Label headlineLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Hyperlink newsURL;

    public void initialize() throws IOException {
        String[] symbols = {"AAPL", "MSFT", "TSLA", "SBUX", "NFLX", "META", "BINANCE:BTCUSDT", "BINANCE:ETHUSDT"};
        Label[] percents = {aaplPercent, msftPercent, tslaPercent, sbuxPercent, nflxPercent, metaPercent, btcPercent, ethPercent};
        Label[] nums = {applNum, msftNum, tslaNum, sbuxNum, nflxNum, metaNum, btcNum, ethNum};
        Label[] prices = {applPrice, msftPrice, tslaPrice, sbuxPrice, nflxPrice, metaPrice, btcPrice, ethPrice};

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(15), event -> {
            try {
                for (int i = 0; i < 8; i++) {
                    Double[] curResult = FinnhubHandler.getStockPrice(symbols[i]);
                    percents[i].setText(String.format("%.2f%%", curResult[2]));
                    nums[i].setText(String.format("%.2f", curResult[1]));
                    prices[i].setText(String.format("%.2f", curResult[0]));

                    if (curResult[2] > 0) {
                        percents[i].setText("+" + percents[i].getText());
                        percents[i].setStyle("-fx-text-fill: #4bb543;");
                        nums[i].setText("+" + nums[i].getText());
                        nums[i].setStyle("-fx-text-fill: #4bb543;");
                    } else {
                        percents[i].setStyle("-fx-text-fill: #b22222;");
                        nums[i].setStyle("-fx-text-fill: #b22222;");
                    }
                }
                String[] news = FinnhubHandler.getRandNews();
                System.out.println(Arrays.toString(news));
                try {
                    Image image = new Image(news[2]);
                    newsImage.setImage(image);
                    newsImage.setPreserveRatio(true);
                    newsImage.setSmooth(true);
                }
                catch (IllegalArgumentException e){
                    Image image2 = new Image("https://www.adviserinvestments.com/wp-content/uploads/are-stocks-overpriced-stocks-overpriced.jpg.webp");
                    newsImage.setImage(image2);
                }
                headlineLabel.setText(news[1]);
                dateLabel.setText(news[0]);
                newsURL.setText(news[3]);
                newsURL.setVisited(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            try {
                for (int i = 0; i < 8; i++) {
                    Double[] curResult = FinnhubHandler.getStockPrice(symbols[i]);
                    percents[i].setText(String.format("%.2f%%", curResult[2]));
                    nums[i].setText(String.format("%.2f", curResult[1]));
                    prices[i].setText(String.format("%.2f", curResult[0]));

                    if (curResult[2] > 0) {
                        percents[i].setText("+" + percents[i].getText());
                        percents[i].setStyle("-fx-text-fill: #4bb543;");
                        nums[i].setText("+" + nums[i].getText());
                        nums[i].setStyle("-fx-text-fill: #4bb543;");
                    } else {
                        percents[i].setStyle("-fx-text-fill: #b22222;");
                        nums[i].setStyle("-fx-text-fill: #b22222;");
                    }
                }
                String[] news = FinnhubHandler.getRandNews();
                System.out.println(Arrays.toString(news));
                try {
                    Image image = new Image(news[2]);
                    newsImage.setImage(image);
                    newsImage.setPreserveRatio(true);
                    newsImage.setSmooth(true);
                }
                catch (IllegalArgumentException e){
                    Image image2 = new Image("https://www.adviserinvestments.com/wp-content/uploads/are-stocks-overpriced-stocks-overpriced.jpg.webp");
                    newsImage.setImage(image2);
                }
                headlineLabel.setText(news[1]);
                dateLabel.setText(news[0]);
                newsURL.setText(news[3]);
                newsURL.setVisited(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        timeline2.setCycleCount(1);
        timeline2.play();
    }

}