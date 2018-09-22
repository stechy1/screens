/*
 *         Copyright 2017 Petr Štechmüller
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *         limitations under the License.
 */

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
     * Přidá dopředu nový node
     *
     * @param node Node, který se má přidat dopředu
     */
    void addChildNode(Node node);

    /**
     * Odebere vybraný node
     *
     * @param node Node, který se má odebrat
     */
    void removeChildNode(Node node);

    void disableScreen();

    void enableScreen();

    /**
     * @return Vrátí {@link Node} který představuje kořenový prvek scény
     */
    Node getContainer();
}
