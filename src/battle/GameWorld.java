/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battle;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.animation.KeyFrame;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


class GameWorld extends Parent {

    Player_one gbtns1;
    Player_two gbtns2;
    Pane gameRoot;

    InputStream is = Files.newInputStream(Paths.get("res/retroBGG.jpg"));
    Image img = new Image(is);
    ImageView imgView;

    InputStream is2 = Files.newInputStream(Paths.get("res/chars.gif"));
    Image img2 = new Image(is2);
    ImageView character2;
    
    InputStream is3 = Files.newInputStream(Paths.get("res/charstop.png"));
    Image img3 = new Image(is3);
    ImageView charStop;

    Keys K1;
    KeyFrame kf;

    Text text1;
    Text text2;

    Text PlayerWon = new Text();

    public GameWorld() throws IOException {

        PlayerWon.setFont(PlayerWon.getFont().font("Gruppo", 40));

        PlayerWon.setFill(Color.LAVENDER);
        PlayerWon.setEffect(new Glow(5));
        PlayerWon.setVisible(false);
        PlayerWon.setX(400);
        PlayerWon.setY(200);

        gameRoot = new Pane();
        gameRoot.setPrefSize(900, 650);

        gbtns1 = new Player_one();
        gbtns2 = new Player_two();

        text2 = new Text(" Player 2 \n Score: " + gbtns2.plyr2.score_player);
        text2.setFont(Font.font("Arial", 20));
        text2.setFill(Color.LIGHTBLUE);
        text2.setEffect(new Glow(5));

        text1 = new Text(" Player 1 \n Score: " + gbtns1.plyr1.score_player);
        text1.setFont(Font.font("Arial", 20));
        text1.setFill(Color.LIGHTBLUE);
        text1.setEffect(new Glow(5));

        text1.setX(120);
        text1.setY(100);

        text2.setX(670);
        text2.setY(100);

        K1 = new Keys();

        character2 = new ImageView(img2);

        character2.setFitWidth(500);
        character2.setFitHeight(326);
        is2.close();
        character2.setX(200);
        character2.setY(300);

        imgView = new ImageView(img);
        imgView.setFitWidth(900);
        imgView.setFitHeight(650);
        is.close();
        
         charStop = new ImageView(img3);

        
        is3.close();
        charStop.setX(285);
        charStop.setY(300);
        charStop.setVisible(false);
        
        


        gameRoot.getChildren().addAll(gbtns1.plyr1.yay1);
        gbtns1.plyr1.yay1.setVisible(false);
        gameRoot.getChildren().addAll(gbtns2.plyr2.yay1);
        gbtns2.plyr2.yay1.setVisible(false);

        gameRoot.getChildren().addAll(gbtns1.Player1, gbtns2.Player2, text1, text2, PlayerWon);

        gameRoot.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        K1.setkeys(keyEvent.getCode(), true);

                    }
                });

        gameRoot.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {

                        if (K1.getkeys(keyEvent.getCode()) == true) {  //if key is not released
                            return;
                        } else {
                            K1.setkeys(keyEvent.getCode(), true);
                        }

                        //check if the key equals the instruction
                        if (K1.Key1State() && (KeyCode.W == gbtns1.check1() || KeyCode.S == gbtns1.check1() || KeyCode.A == gbtns1.check1() || KeyCode.D == gbtns1.check1())) {
                            if (K1.keyUserClicked == gbtns1.check1()) {

                                gbtns1.plyr1.score_player++;
                                text1.setText(" Player 1 \n Score: " + gbtns1.plyr1.score_player);
                                gbtns1.plyr1.ResetButton(gbtns1.plyr1.hist);

                            } //not equal
                            else {

                                gbtns1.plyr1.WrongButton(gbtns1.plyr1.hist);
                            }

                        }

                        if (K1.Key2State() && (KeyCode.UP == gbtns2.check2() || KeyCode.DOWN == gbtns2.check2() || KeyCode.RIGHT == gbtns2.check2() || KeyCode.LEFT == gbtns2.check2())) {

                            if (K1.keyUserClicked == gbtns2.check2()) {
                                //check if the key equals the instruction
                                
                                gbtns2.plyr2.score_player++;                              
                                text2.setText(" Player 2 \n Score: " + gbtns2.plyr2.score_player);
                                gbtns2.plyr2.ResetButton(gbtns2.plyr2.hist);

                            } else {

                                gbtns2.plyr2.WrongButton(gbtns2.plyr2.hist);
                            }

                        }

                    }

                });

        gameRoot.setOnKeyReleased(
                e -> {
                    K1.setkeys(e.getCode(), false);

                }
        );

    }

}
