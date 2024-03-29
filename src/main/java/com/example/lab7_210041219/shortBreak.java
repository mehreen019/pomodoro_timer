package com.example.lab7_210041219;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class shortBreak {

    @FXML
    AnchorPane ap;
    @FXML
    Rectangle rect;
    public Parent root;
    private Scene scene;
    private Stage stage;

    @FXML
    private Label mainTime;

    @FXML
    protected Button pomodoro;
    @FXML
    protected Button long_break;
    @FXML
    protected Button short_break;
    @FXML
    protected Button pause;

    private int secondsLeft = 5*60;
    public int sessions=0;
    private Boolean sessionStart=false;
    private Boolean sessionPause=false;
    private Timeline timeline;

    @FXML
    protected void onStartClick(ActionEvent btnClick){
        pause.setText("PAUSE");
        this.startTimer(btnClick);
    }

    public void switchToLongBreak(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view-3.fxml"));
        //loader.setController(this);
        root = loader.load();
        root.setStyle("-fx-background-color: #c7c2ef;");
        longBreak sc = loader.getController();
        sc.init(sessions, e);
        System.out.println("sessions: " + sessions);
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene =  new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToPomodoro(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        //loader.setController(this);
        root = loader.load();
        root.setStyle("-fx-background-color: #f1b9b9;");
        HelloController hc = loader.getController();
        hc.init(sessions, e);
        System.out.println("sessions: " + sessions);
        if(e==null) System.out.println("btn is still null");
        //assert e != null;
        stage = (Stage) pause.getScene().getWindow();
        scene =  new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void startTimer(ActionEvent btnClick) {
        if(!sessionStart){
            sessionStart=true;
            ap.setStyle("-fx-background-color: #acdcd6;");
            rect.setFill(Color.web("#ADD8E6FF"));
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            secondsLeft--;
            updateTimerLabel();
            if (secondsLeft <= 0) {
                timeline.stop();
                pause.setText("START");
                System.out.println("Timer has finished!");
                try {
                    this.switchToPomodoro(btnClick);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    else if(sessionStart && !sessionPause){
            ap.setStyle("-fx-background-color: #ADD8E6FF;");
            rect.setFill(Color.web("#acdcd6"));
            pause.setText("START");
        sessionPause=true;
        timeline.pause();
    }
    else if(sessionStart && sessionPause){
            ap.setStyle("-fx-background-color: #acdcd6;");
            rect.setFill(Color.web("#ADD8E6FF"));
        pause.setText("PAUSE");
        sessionPause=false;
        timeline.play();
    }
    }

    public void init(int s, ActionEvent btnClick){
        sessions=s;
        sessionStart=false;
        sessionPause=false;
        if(sessions==1){
            pause.setText("PAUSE");
            if(btnClick==null) System.out.println("btn is still null");
            this.startTimer(btnClick);
        }
    }

    private void updateTimerLabel() {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft % 60;
        String timeString = String.format("%02d:%02d", minutes, seconds);
        mainTime.setText(timeString);
    }
}
