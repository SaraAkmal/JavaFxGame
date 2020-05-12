/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

/**
 *
 * @author sara
 */
class Player extends Parent {

    Button btnUp;
    Button btnDown;
    Button btnRight;
    Button btnLeft;

    KeyFrame btntrn;
    Timeline time;
    Button turn = new Button();
    EventHandler<ActionEvent> btnTurn;

    int hist;

    int score_player;
    BooleanProperty yay = new SimpleBooleanProperty(false);

    InputStream is3 = Files.newInputStream(Paths.get("res/yay.gif"));
    Image bonus = new Image(is3);
    ImageView yay1;
    int ScorePaused;
    
     File file2 = new File("Power_Up_Ray-Mike_Koenig-800933783.mp3");

   AudioClip clip2 = new AudioClip(file2.toURI().toString());

    Player() throws IOException {

        yay1 = new ImageView(bonus);

        yay1.setFitWidth(280);
        yay1.setFitHeight(280);
        is3.close();
        yay1.setX(280);
        yay1.setY(5);
        btnUp = new Button("^");
        btnUp.setStyle("-fx-text-fill: white;-fx-font-size: 24; -fx-opacity: 0.8; -fx-background-color:MIDNIGHTBLUE;-fx-pref-height: 50px;-fx-pref-width: 50px;");

        btnDown = new Button("v");
        btnDown.setStyle("-fx-text-fill: white;-fx-font-size: 24; -fx-opacity: 0.8; -fx-background-color:MIDNIGHTBLUE;-fx-pref-height: 50px;-fx-pref-width: 50px;");

        btnRight = new Button(">");
        btnRight.setStyle("-fx-text-fill: white;-fx-font-size: 24; -fx-opacity: 0.8; -fx-background-color:MIDNIGHTBLUE;-fx-pref-height: 50px;-fx-pref-width: 50px;");

        btnLeft = new Button("<");
        btnLeft.setStyle("-fx-text-fill: white;-fx-font-size: 24; -fx-opacity: 0.8; -fx-background-color:MIDNIGHTBLUE;-fx-pref-height: 50px;-fx-pref-width: 50px;");

        btnTurn = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //yay.set(false);

                int x = (int) (Math.random() * 4 + 1);
                while (x == hist) {
                    x = (int) (Math.random() * 4 + 1);

                }
                for (int i = 1; i <= 4; i++) {
                    Buttonturn(i).setStyle("-fx-text-fill: white;-fx-font-size: 24; -fx-opacity: 0.8; -fx-background-color:MIDNIGHTBLUE;-fx-pref-height: 50px;-fx-pref-width: 50px;");
                }

                Buttonturn(hist).setStyle("-fx-text-fill: white;-fx-font-size: 24; -fx-opacity: 0.8; -fx-background-color:MIDNIGHTBLUE;-fx-pref-height: 50px;-fx-pref-width: 50px;");

                Buttonturn(x).setStyle("-fx-font-size: 20; -fx-opacity: 0.8; -fx-background-color:LAVENDER;-fx-pref-height:50px;-fx-pref-width: 50px");

                hist = x;

                if (((score_player % 5) == 0) && score_player != 0 && ScorePaused != score_player) {
                    yay.set(true);
                }
                ScorePaused = score_player;

            }
        };

        time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);

        btntrn = new KeyFrame(Duration.millis(500), btnTurn);
        time.getKeyFrames().add(btntrn);
        time.play();

        yay.addListener((observable, oldvalue, newvalue) -> {
            //primaryStage.close();
            yay1.setVisible(true);
            clip2.play();

            Task<Void> sleeper = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                    }
                    return null;
                }
            };
            sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    yay1.setVisible(false);
                    yay.set(false);
                }
            });
            new Thread(sleeper).start();

        } //add gif then removes by delaying
        );

    }

    public Button Buttonturn(int x) {
        switch (x) {
            case 1:
                turn = btnUp;
                break;
            case 2:
                turn = btnRight;
                break;
            case 3:
                turn = btnLeft;
                break;
            case 4:
                turn = btnDown;
                break;
            default:
                turn = btnUp;

        }
        return turn;

    }

    void ResetButton(int x) {
        Buttonturn(x).setStyle("-fx-text-fill: white ;-fx-font-size: 24; -fx-opacity: 0.8; -fx-background-color:GREEN;-fx-pref-height:50px;-fx-pref-width: 50px;");
        hist = 5;

    }

    int WrongButton(int x) {
        Buttonturn(x).setStyle("-fx-text-fill: white ;-fx-font-size: 24; -fx-opacity: 0.8; -fx-background-color:RED;-fx-pref-height:50px;-fx-pref-width: 50px;");
        hist = 5;
        return x;

    }

}
