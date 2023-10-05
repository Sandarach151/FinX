package com.example.finx;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;

public class StockViewApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        AnchorPane pane = new AnchorPane();
        pane.setStyle("-fx-background-color: black");
        Label logo = new Label("FinX");
        logo.setStyle("-fx-text-fill: white");
        logo.setMinSize(1280, 800);
        logo.setContentDisplay(ContentDisplay.CENTER);
        logo.setAlignment(Pos.CENTER);
        logo.setFont(new Font("Agency FB", 130));
        pane.getChildren().add(logo);
        stage.setScene(new Scene(pane, 1280, 800));
        stage.setResizable(false);
        stage.getIcons().add(new Image(LoginApplication.class.getResourceAsStream("Images/Logo.png")));
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), logo);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(Timeline.INDEFINITE);
        fadeIn.setAutoReverse(true);
        fadeIn.play();

        Task<Scene> task = new Task<Scene>() {
            @Override
            protected Scene call() throws Exception {
                Scene scene = null;
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(LoginApplication.class.getResource("View/stock-view.fxml"));
                    scene = new Scene(fxmlLoader.load(), 1280, 800);
                    Thread.sleep(5000);
                } catch (IOException | InterruptedException e) {
                    System.out.println(e);
                }
                return scene;
            }
        };

        task.setOnSucceeded(e -> {
            fadeIn.stop();
            Scene scene = task.getValue();
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image(LoginApplication.class.getResourceAsStream("Images/Logo.png")));
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                    Platform.exit();
                    System.exit(0);
                }
            });
        });

        new Thread(task).start();
    }

    public static void main(String[] args) {launch();}
}
