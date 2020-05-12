package battle;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javafx.scene.control.TextField;

import javafx.scene.media.AudioClip;

import javafx.util.Duration;

class Score implements Serializable {

    private int score;
    private String name;

    public int getScore() {
        return score;
    }

    public String getNaam() {
        return name;
    }

    public Score(String name, int score) {
        this.score = score;
        this.name = name;
    }
}

class ScoreComparator implements Comparator<Score> {

    public int compare(Score score1, Score score2) {

        int sc1 = score1.getScore();
        int sc2 = score2.getScore();

        if (sc1 > sc2) {
            return -1;
        } else if (sc1 < sc2) {
            return +1;
        } else {
            return 0;
        }
    }
}

class HighscoreManager {

    private ArrayList<Score> scores;

    private static final String HIGHSCORE_FILE = "scores.dat";

    ObjectOutputStream outputStream = null;
    ObjectInputStream inputStream = null;

    public HighscoreManager() {
        //initialising the scores-arraylist
        scores = new ArrayList<Score>(4);
        scores.add(0, new Score("", 0));
        scores.add(1, new Score("", 0));
        scores.add(2, new Score("", 0));
        scores.add(3, new Score("", 0));
    }

    private void sort() {
        ScoreComparator comparator = new ScoreComparator();
        Collections.sort(scores, comparator);
    }

    public ArrayList<Score> getScores() {
        loadScoreFile();
        sort();
        return scores;
    }

    public void addScore(String name, int score) {
        loadScoreFile();
        scores.add(new Score(name, score));
        updateScoreFile();
    }

    public void loadScoreFile() {
        try {
            inputStream = new ObjectInputStream(new FileInputStream(HIGHSCORE_FILE));
            scores = (ArrayList<Score>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("[Laad] FNF Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("[Laad] IO Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("[Laad] CNF Error: " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Laad] IO Error: " + e.getMessage());
            }
        }
    }

    public void updateScoreFile() {
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE));
            outputStream.writeObject(scores);
        } catch (FileNotFoundException e) {
            System.out.println("[Update] FNF Error: " + e.getMessage() + ",the program will try and make a new file");
        } catch (IOException e) {
            System.out.println("[Update] IO Error: " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Update] Error: " + e.getMessage());
            }
        }
    }

    public String getHighscoreString() {

        String highscoreString = "";

        ArrayList<Score> scores;
        scores = getScores();

        int i = 0;

        while (i < 4) {
            highscoreString += (i + 1) + ".\t" + scores.get(i).getNaam() + "\t\t" + scores.get(i).getScore() + "\n";
            i++;
        }
        return highscoreString;
    }

}

class Keys {

    private boolean[] keysarray;
    KeyCode keyUserClicked;

    public Keys() {
        keysarray = new boolean[8];
    }

    private int KCTI(KeyCode k) {
        switch (k) {
            case UP:
                return 4;
            case DOWN:
                return 5;
            case LEFT:
                return 6;
            case RIGHT:
                return 7;
            case W:
                return 0;
            case S:
                return 1;
            case A:
                return 2;
            case D:
                return 3;
            default:
                return -1;
        }
    }

    public void setkeys(KeyCode k, boolean state) {
        int i = KCTI(k);
        if (i == -1) {
            return;
        }
        keysarray[i] = state;
        keyUserClicked = k;
    }

    public void setAllkeysFalse() {
        for (int i = 0; i < 8; i++) {
            keysarray[i] = false;
        }

    }

    public boolean getkeys(KeyCode k) {
        int i = KCTI(k);
        if (i == -1) {
            return false;
        }
        return keysarray[i];
    }

    public boolean Key1State() {
        if (getkeys(KeyCode.W) || getkeys(KeyCode.S) || getkeys(KeyCode.D) || getkeys(KeyCode.A)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean Key2State() {
        if (getkeys(KeyCode.UP) || getkeys(KeyCode.DOWN) || getkeys(KeyCode.LEFT) || getkeys(KeyCode.RIGHT)) {
            return true;
        } else {
            return false;
        }
    }

}

class Player_one extends Parent {

    GridPane Player1;
    Player plyr1;

    KeyFrame btntrn;
    Timeline time;

    //int score_player_one;
    KeyCode ky1;

    public Player_one() throws IOException {

        plyr1 = new Player();

        Player1 = new GridPane();
        Player1.setPrefSize(900, 650);

        Player1.setRowIndex(plyr1.btnUp, 100);
        Player1.setColumnIndex(plyr1.btnUp, 7);

        Player1.setRowIndex(plyr1.btnDown, 102);
        Player1.setColumnIndex(plyr1.btnDown, 7);

        Player1.setRowIndex(plyr1.btnRight, 102);
        Player1.setColumnIndex(plyr1.btnRight, 8);

        Player1.setRowIndex(plyr1.btnLeft, 102);
        Player1.setColumnIndex(plyr1.btnLeft, 6);

        Player1.setVgap(5);
        Player1.setHgap(5);

        Player1.getChildren().addAll(plyr1.btnUp, plyr1.btnDown, plyr1.btnRight, plyr1.btnLeft);

    }

    public KeyCode check1() {
        switch (plyr1.hist) {

            case 1:
                ky1 = KeyCode.W;
                break;
            case 2:
                ky1 = KeyCode.D;
                break;
            case 3:
                ky1 = KeyCode.A;
                break;
            case 4:
                ky1 = KeyCode.S;
                break;
            case 5:
                ky1 = null;
                break;

        }
        return ky1;
    }

}

class Player_two extends Parent {

    GridPane Player2;
    Player plyr2;

    KeyFrame btntrn2;
    Timeline time2;
    //int score_player_two;

    KeyCode ky2;

    public Player_two() throws IOException {
        plyr2 = new Player();

        Player2 = new GridPane();

        Player2.setRowIndex(plyr2.btnUp, 100);
        Player2.setColumnIndex(plyr2.btnUp, 136);

        Player2.setRowIndex(plyr2.btnDown, 102);
        Player2.setColumnIndex(plyr2.btnDown, 136);

        Player2.setRowIndex(plyr2.btnRight, 102);
        Player2.setColumnIndex(plyr2.btnRight, 137);

        Player2.setRowIndex(plyr2.btnLeft, 102);
        Player2.setColumnIndex(plyr2.btnLeft, 135);

        Player2.setVgap(5);
        Player2.setHgap(5);

        Player2.getChildren().addAll(plyr2.btnUp, plyr2.btnDown, plyr2.btnRight, plyr2.btnLeft);

        time2 = new Timeline();
        time2.setCycleCount(Timeline.INDEFINITE);

    }

    public KeyCode check2() {
        switch (plyr2.hist) {

            case 1:
                ky2 = KeyCode.UP;
                break;
            case 2:
                ky2 = KeyCode.RIGHT;
                break;
            case 3:
                ky2 = KeyCode.LEFT;
                break;
            case 4:
                ky2 = KeyCode.DOWN;
                break;
            case 5:
                ky2 = null;
                break;

        }
        return ky2;
    }

}

public class Battle extends Application {

    AudioClip clip;
    AudioClip clip1;
    AudioClip clip4;
    AudioClip clip3;
    GameMenu gameMenu;
    ScoreMenu scoreMenu;
    GameWorld gameWorld;

    time timer = new time();

    BooleanProperty finishGame = new SimpleBooleanProperty(false);
    HighscoreManager hm = new HighscoreManager();

    @Override
    public void start(Stage primaryStage) throws IOException {

        File file = new File("Button-SoundBible.com-1420500901.mp3");

        clip = new AudioClip(file.toURI().toString());

        File file1 = new File("bensound-retrosoul.mp3");

        clip1 = new AudioClip(file1.toURI().toString());

        File file2 = new File("WinSoundEffect.mp3");

        clip4 = new AudioClip(file2.toURI().toString());

        primaryStage.setTitle("Battle");
        primaryStage.setResizable(false);

        Stage playername = new Stage();
        playername.setTitle("Type your names");
        Button b1 = new Button("Done");
        TextField t1 = new TextField("Player1");
        TextField t2 = new TextField("Player2");
        VBox v1 = new VBox(t1, t2, b1);
        Scene s1 = new Scene(v1, 100, 100);

        primaryStage.sizeToScene();
        AnchorPane root = new AnchorPane();
        root.setPrefSize(900, 650);

        scoreMenu = new ScoreMenu();
        AnchorPane rootscore = new AnchorPane();
        rootscore.getChildren().addAll(scoreMenu.imgView, scoreMenu.menu1);
        Scene scoremenu = new Scene(rootscore);

        gameMenu = new GameMenu();
        root.getChildren().addAll(gameMenu.imgView, gameMenu.menu1);
        Scene Menu = new Scene(root);

        gameWorld = new GameWorld();
        AnchorPane rootWorld = new AnchorPane();
        rootWorld.getChildren().addAll(gameWorld.imgView, gameWorld.gameRoot, gameWorld.character2, timer, gameWorld.charStop);
        Scene gworld = new Scene(rootWorld);
        rootWorld.setPrefSize(900, 650);

        primaryStage.setScene(Menu);
        primaryStage.show();

        gameMenu.btnPlay.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                playername.setScene(s1);
                playername.show();
                b1.setOnAction(e -> {
                    playername.close();
                    timer.begin();
                    primaryStage.setScene(gworld);

                    primaryStage.show();

                    clip.play();

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Battle.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    clip1.play();

                });

            }
        });

        gameMenu.btnExit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clip.play();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you Sure ", ButtonType.YES, ButtonType.NO);

                ButtonType r = alert.showAndWait().orElse(ButtonType.NO);

                if (ButtonType.YES.equals(r)) {
                    primaryStage.close();
                }

            }
        });

        gameMenu.btnScore.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                primaryStage.setScene(scoremenu);
                primaryStage.show();

                clip.play();

            }
        });

        scoreMenu.Back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                primaryStage.setScene(Menu);
                primaryStage.show();

                clip.play();

            }
        });

        primaryStage.setOnCloseRequest(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to close this game :( ", ButtonType.YES, ButtonType.NO);
            ButtonType result = alert.showAndWait().orElse(ButtonType.NO);

            if (ButtonType.YES.equals(result)) {
                primaryStage.close();
            } else {
                e.consume();
            }
        });

        Timeline inGame = new Timeline();
        inGame.setCycleCount(Timeline.INDEFINITE);

        KeyFrame stop = new KeyFrame(Duration.millis(800), whenToStop);
        inGame.getKeyFrames().add(stop);
        inGame.play();

        finishGame.addListener((observable, oldvalue, newvalue) -> {
            hm.addScore(t2.getText(), gameWorld.gbtns2.plyr2.score_player);
            hm.addScore(t1.getText(), gameWorld.gbtns1.plyr1.score_player);
            clip4.play();

            if (gameWorld.gbtns1.plyr1.score_player > gameWorld.gbtns2.plyr2.score_player) {
                gameWorld.PlayerWon.setText(t1.getText() + "\n" + " Won!");
            } else if (gameWorld.gbtns1.plyr1.score_player < gameWorld.gbtns2.plyr2.score_player) {
                gameWorld.PlayerWon.setText(t2.getText() + "\n" + " Won!");
            } else {

                gameWorld.PlayerWon.setX(400);
                gameWorld.PlayerWon.setY(200);

                gameWorld.PlayerWon.setText("Draw");
            }

            gameWorld.character2.setVisible(false);
            gameWorld.charStop.setVisible(true);

            gameWorld.PlayerWon.setVisible(true);

        });

    }

    EventHandler whenToStop = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            if (timer.TimeUp == true) {
                finishGame.set(true);
                gameWorld.gbtns2.plyr2.time.stop();
                gameWorld.gbtns1.plyr1.time.stop();
                clip1.stop();

            }

        }

    };

    public static void main(String[] args) {
        launch(args);
    }
}
