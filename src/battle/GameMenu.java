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
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


class GameMenu extends Parent {

    VBox menu1;
    MenuButtons btnPlay;
    MenuButtons btnScore;
    MenuButtons btnExit;
    InputStream is = Files.newInputStream(Paths.get("res/retroBGG.jpg"));
    Image img = new Image(is);
    ImageView imgView;

    public GameMenu() throws IOException {
        imgView = new ImageView(img);
        imgView.setFitWidth(900);
        imgView.setFitHeight(650);
        is.close();

        menu1 = new VBox(15);
        //menu1.setAlignment(Pos.CENTER);

        menu1.setTranslateX(270);
        menu1.setTranslateY(120);

        btnPlay = new MenuButtons("Play");
        btnScore = new MenuButtons("Score");
        btnExit = new MenuButtons("Exit");

        menu1.getChildren().addAll(btnPlay, btnScore, btnExit);

    }

}
