package cz.stechy.screens.animation;

import javafx.animation.Transition;
import javafx.scene.Node;

/**
 * Rozhraní pro metody na animaci mezi okny
 */
public interface IScreenAnimator {

    /**
     * Vytvoří animaci pro zobrazení prvního screenu
     *
     * @param node Kořenový node, nad kterým se bude animace volat
     * @return Animaci pro zobrazení prvního screenu
     */
    Transition getShowAnimation(Node node);

    /**
     * Vytvoří animaci pro zmizení screenu
     *
     * @param node Kořenový node, nad kterým se bude animace volat
     * @return Animaci pro zmizení screenu
     */
    Transition getHideAnimation(Node node);

}
