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
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


class ScoreMenu extends Parent {

    VBox menu1;
    HighscoreManager h;
    MenuButtons Back;
    InputStream is = Files.newInputStream(Paths.get("res/retroBGG.jpg"));
    Image img = new Image(is);
    ImageView imgView;

    public ScoreMenu() throws IOException {
        h = new HighscoreManager();
        imgView = new ImageView(img);
        imgView.setFitWidth(900);
        imgView.setFitHeight(650);
        is.close();
        menu1 = new VBox(15);

        Text lbl = new Text(h.getHighscoreString());
        lbl.setFill(Color.LAVENDER);
        lbl.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 40));
        lbl.setEffect(new Glow(5));

        menu1.setTranslateX(270);
        menu1.setTranslateY(120);

        Back = new MenuButtons("Back");

        menu1.getChildren().addAll(Back, lbl);

    }

}
