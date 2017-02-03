package cz.stechy.screens.base;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

/**
 * Rozhraní pro metody na animaci mezi screeny
 */
public interface IScreenTransition {

    /**
     * Vytvoří animaci pro zobrazení prvního screenu
     *
     * @param node Kořenový node, nad kterým se bude animace volat
     * @return Animaci pro zobrazení prvního screenu
     */
    Timeline getFirstTimeShowAnimation(Node node);

    /**
     * Vytvoří animaci pro přechod mezi dvěma screeny
     *
     * @param node Kořenový node, nad kterým se bude animace volat
     * @return Animaci pro přechod mezi dvěma screeny
     */
    Timeline getChangingAnimation(Node node, EventHandler<ActionEvent> event);

    /**
     * Vytvoří animaci pro zmizení screenu
     *
     * @param node Kořenový node, nad kterým se bude animace volat
     * @return Animaci pro zmizení screenu
     */
    Timeline getHideAnimation(Node node);
}
