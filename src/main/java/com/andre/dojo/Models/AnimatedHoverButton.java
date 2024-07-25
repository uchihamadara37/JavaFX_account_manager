package com.andre.dojo.Models;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class AnimatedHoverButton extends Button {

    private Line underline;

    public AnimatedHoverButton(String text) {
        super(text);
//        getStyleClass().add("button-filter");

        underline = new Line();
        underline.setStroke(javafx.scene.paint.Color.WHITE);
        underline.setStrokeWidth(2);
        underline.setVisible(false);

        StackPane.setAlignment(underline, javafx.geometry.Pos.BOTTOM_CENTER);

        StackPane stackPane = new StackPane(this, underline);

        setOnMouseEntered(event -> animateUnderline(true));
        setOnMouseExited(event -> animateUnderline(false));
    }

    private void animateUnderline(boolean show) {
        underline.setVisible(true);
        double endWidth = show ? getWidth() * 0.8 : 0;
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(underline.startXProperty(), underline.getStartX()),
                        new KeyValue(underline.endXProperty(), underline.getEndX()),
                        new KeyValue(underline.opacityProperty(), underline.getOpacity())
                ),
                new KeyFrame(Duration.millis(200),
                        new KeyValue(underline.startXProperty(), getWidth() / 2 - endWidth / 2),
                        new KeyValue(underline.endXProperty(), getWidth() / 2 + endWidth / 2),
                        new KeyValue(underline.opacityProperty(), show ? 1.0 : 0.0)
                )
        );
        timeline.play();
    }
}

