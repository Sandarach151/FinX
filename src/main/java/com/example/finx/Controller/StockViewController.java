package com.example.finx.Controller;

import com.example.finx.ExploreApplication;
import com.example.finx.Model.Company;
import com.example.finx.Model.CompanyNewsDatabase;
import com.example.finx.Model.NewsArticle;
import com.example.finx.Model.StockCandleChart;
import com.example.finx.Others.DBHandler;
import com.example.finx.Others.FinnhubHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

public class StockViewController {

    @FXML
    private Label ExploreBanner;

    @FXML
    private Button exploreBtn;

    @FXML
    private ListView<HBox> newsListView;

    @FXML
    private AnchorPane page;

    @FXML
    private HBox topHbox;

    @FXML
    private GridPane chartInfoPane;

    @FXML
    private VBox companyInfo;

    @FXML
    void onExploreBtnClicked(MouseEvent event) throws IOException {
        ExploreApplication app = new ExploreApplication();
        app.start(new Stage());
        Stage primary = (Stage) this.exploreBtn.getScene().getWindow();
        primary.close();
    }

    public void initialize() throws IOException {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setForceZeroInRange(false);
        LineChart<String, Number> stockChart = new LineChart<>(xAxis, yAxis);
        stockChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        StockCandleChart chartValues = FinnhubHandler.getCandles(DBHandler.getCurrentStock());
        ArrayList<Instant> timestamps = chartValues.getTimestamps();
        ArrayList<Double> values = chartValues.getPriceValues();
        Double[] valuesByMonth = new Double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        Integer[] nums = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        for(int i=0; i<timestamps.size(); i++){
            ZonedDateTime time = timestamps.get(i).atZone(ZoneId.of("GMT+8"));
            int month = time.getMonth().getValue()-1;
            valuesByMonth[month]+=values.get(i);
            nums[month]++;
        }
        for(int i=0; i<12; i++){
            valuesByMonth[i]/=nums[i];
        }
        ZonedDateTime time = Instant.now().atZone(ZoneId.of("GMT+8"));
        int curMonth = time.getMonth().getValue()-1;
        for(int i=0; i<12; i++){
            series.getData().add(new XYChart.Data<>(months[(curMonth+1+i)%12], valuesByMonth[(curMonth+1+i)%12]));
        }
        stockChart.getData().add(series);
        stockChart.setLegendVisible(false);
        chartInfoPane.add(stockChart, 0, 0);

        companyInfo.setPadding(new Insets(0, 0, 0, 20));
        companyInfo.setSpacing(7);
        Company company = FinnhubHandler.getCompanyInfo(DBHandler.getCurrentStock());
        String[] properties = new String[]{"Name", "Country", "Currency", "Industry", "Ticker", "Exchange", "IPO", "Market Cap", "Outstanding Shares", "Website"};
        String[] companyValues = new String[]{company.getName(), company.getCountry(), company.getCurrency(), company.getIndustry(), company.getTicker(), company.getExchange(), company.getIpo().toString(), String.valueOf(company.getMarketCapitalisation()), String.valueOf(company.getShareOutstanding()), company.getWeburl()};
        for(int i=0; i<properties.length; i++){
            Label label = new Label(properties[i]+": "+companyValues[i]);
            label.setWrapText(true);
            label.setFont(new Font("Arial", 15));
            label.setStyle("-fx-text-fill: #fffff0");
            companyInfo.getChildren().add(label);
        }
        ExploreBanner.setText(companyValues[0]);

        ArrayList<NewsArticle> database = FinnhubHandler.getCompanyNews(DBHandler.getCurrentStock()).getNewsDB();
        for(int i=0; i<database.size(); i++){
            int finalI = i;
            Platform.runLater(() -> {
                NewsArticle cur = database.get(finalI);
                HBox hbox = new HBox();
                hbox.setSpacing(20);
                VBox vbox = new VBox();
                Label headline = new Label(cur.getHeadline());
                headline.setFont(new Font("Times New Roman", 18));
                headline.setStyle("-fx-text-fill: #ffffff");
                Label summary = new Label(cur.getSummary());
                summary.setStyle("-fx-text-fill: #ffffff");
                summary.setMaxWidth(1000);
                Hyperlink url = new Hyperlink(cur.getUrl());
                Label date = new Label(Date.from(cur.getPublishTime()).toString());
                date.setStyle("-fx-text-fill: #ffffff");
                vbox.getChildren().add(headline);
                vbox.getChildren().add(summary);
                vbox.getChildren().add(url);
                vbox.getChildren().add(date);
                vbox.setSpacing(10);
                vbox.setPadding(new Insets(10, 20, 10, 20));
                hbox.getChildren().add(vbox);
                newsListView.getItems().add(hbox);
            });
        }
    }

}
