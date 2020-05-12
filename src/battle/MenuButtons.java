/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battle;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;



class MenuButtons extends StackPane {

    private Text text;

    public MenuButtons(String name) {
        text = new Text(name);
        text.setFont(text.getFont().font("Gruppo", 40));
        text.setFill(Color.LAVENDER);
        text.setEffect(new Glow(5));

        Rectangle btn = new Rectangle(350, 50);
        btn.setOpacity(0.2);
        btn.setFill(Color.VIOLET);

        setAlignment(Pos.CENTER);
        setRotate(-0.5);

        getChildren().addAll(btn, text);

        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                btn.setOpacity(0.7);
                btn.setTranslateX(10);
                text.setTranslateX(10);
                btn.setFill(Color.WHITE);
                text.setFill(Color.BLACK);

            }

        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                btn.setOpacity(0.2);
                btn.setTranslateX(0);
                text.setTranslateX(0);
                btn.setFill(Color.VIOLET);
                text.setFill(Color.LAVENDER);

            }

        });

    }

}
