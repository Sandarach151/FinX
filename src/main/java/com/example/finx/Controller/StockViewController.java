package com.example.finx.Controller;

import com.example.finx.ExploreApplication;
import com.example.finx.Model.CompanyNewsDatabase;
import com.example.finx.Model.NewsArticle;
import com.example.finx.Others.DBHandler;
import com.example.finx.Others.FinnhubHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

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
    void onExploreBtnClicked(MouseEvent event) throws IOException {
        ExploreApplication app = new ExploreApplication();
        app.start(new Stage());
        Stage primary = (Stage) this.exploreBtn.getScene().getWindow();
        primary.close();
    }

    public void initialize() throws IOException {
        ArrayList<NewsArticle> database = FinnhubHandler.getCompanyNews(DBHandler.getCurrentStock()).getNewsDB();
        for(int i=0; i<database.size(); i++){
            int finalI = i;
            Platform.runLater(() -> {
                NewsArticle cur = database.get(finalI);
                HBox hbox = new HBox();
                hbox.setSpacing(20);
                ImageView imageview = new ImageView();
                imageview.setFitWidth(150);
                imageview.setPreserveRatio(true);
                if(!cur.getImageURL().equals("")){
                    imageview.setImage(new Image(cur.getImageURL()));
                }
                hbox.getChildren().add(imageview);
                VBox vbox = new VBox();
                Label headline = new Label(cur.getHeadline());
                Hyperlink url = new Hyperlink(cur.getUrl());
                Label date = new Label(cur.getPublishTime().toString());
                vbox.getChildren().add(headline);
                vbox.getChildren().add(url);
                vbox.getChildren().add(date);
                vbox.setSpacing(10);
                hbox.getChildren().add(vbox);
                newsListView.getItems().add(hbox);
            });
        }
    }

}
