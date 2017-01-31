package cz.stechy.screens.base;

import javafx.scene.Node;

/**
 * Rozhraní definující metody pro hlavní kontroler screenů
 */
public interface IMainScreen {

    /**
     * Nastaví node do scény
     *
     * @param node Node, který má být zobrazen
     */
    void setChildNode(Node node);

    /**
     * @return Vrátí {@link Node} který představuje kořenový prvek scény
     */
    Node getContainer();
}
