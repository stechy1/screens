package cz.stechy.screens.transition;

import cz.stechy.screens.base.IScreenTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Created by petr on 3.2.17.
 */
public class OpacityTransition implements IScreenTransition {

    @Override
    public Timeline getFirstTimeShowAnimation(Node node) {
        final DoubleProperty opacity = node.opacityProperty();
        return new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
            new KeyFrame(new Duration(10), new KeyValue(opacity, 1.0))
        );
    }

    @Override
    public Timeline getChangingAnimation(Node node, EventHandler<ActionEvent> event) {
        final DoubleProperty opacity = node.opacityProperty();
        return new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
            new KeyFrame(new Duration(650), event)
        );
    }

    @Override
    public Timeline getHideAnimation(Node node) {
        final DoubleProperty opacity = node.opacityProperty();
        return new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
            new KeyFrame(new Duration(325), new KeyValue(opacity, 1.0))
        );
    }
}
