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

import cz.stechy.screens.Bundle;
import cz.stechy.screens.Notification;
import javafx.geometry.Bounds;
import javafx.scene.Node;

/**
 * Rozhraní definující metody pro hlavní kontroler screenu
 */
public interface IScreenManager {

    int NO_ACTION = -1;

    /**
     * Zobrazí screen
     *
     * @param name Název screenu
     * @param bundle Parametry, které se předají volanému screenu
     */
    default void showScreen(final String name, final Bundle bundle) {
        showScreenForResult(name, NO_ACTION, bundle);
    }

    /**
     * Zobrazí screen
     *
     * @param name Název screenu
     * @param actionId ID akce, na kterou se bude později reagovat
     * @param bundle Parametry, které se předají volanému screenu
     */
    void showScreenForResult(final String name, final int actionId, final Bundle bundle);

    /**
     * Zobrazí screen v novém dialogu
     *
     * @param name Název screenu
     * @param bundle Parametry, které se předají volanému screenu
     */
    default void showDialog(final String name, final Bundle bundle) {
        showDialogForResult(name, NO_ACTION, bundle);
    }

    /**
     * Zobrazí screen v novém dialogu
     *
     * @param name Název screenu
     * @param actionId ID akce, na kterou se bude později reagovat
     * @param bundle Parametry, které se předají volanému screenu
     */
    void showDialogForResult(final String name, final int actionId, final Bundle bundle);

    /**
     * Zobrazí popup dialog na pozici získané z rodičovského prvku
     *
     * @param name Název screenu
     * @param bundle Parametry, které se předají popup dialogu
     * @param parentNode Rodičovský node, ke kterému se dialog "připne"
     */
    default void showPopup(final String name, final Bundle bundle, Node parentNode) {
        showPopupForResult(name, NO_ACTION, bundle, parentNode);
    }

    /**
     * Zobrazí popup dialog na pozici získané z rodičovského prvku
     *
     * @param name Název screenu
     * @param bundle Parametry, které se předají popup dialogu
     * @param x X-ová souřadnice popup dialogu
     * @param y Y-ová souřadnice popup dialogu
     */
    default void showPopup(final String name, final Bundle bundle, double x, double y) {
        showPopupForResult(name, NO_ACTION, bundle, x, y);
    }

    /**
     * Zobrazí popup dialog na pozici získané z rodičovského prvku
     *
     * @param name Název screenu
     * @param actionId  ID akce, na kterou se bude později reagovat
     * @param bundle Parametry, které se předají popup dialogu
     * @param parentNode Rodičovský node, ke kterému se dialog "připne"
     */
    default void showPopupForResult(final String name, final int actionId, final Bundle bundle, Node parentNode) {
        Bounds positionInScreen = parentNode.localToScreen(parentNode.getBoundsInLocal());
        double width = positionInScreen.getMaxX() - positionInScreen.getMinX();
        double height = positionInScreen.getMaxY() - positionInScreen.getMinY();
        showPopupForResult(name, actionId, bundle, positionInScreen.getMinX() + width / 2, positionInScreen.getMinY() + height / 2);
    }

    /**
     * Zobrazí popup dialog na pozici získané z rodičovského prvku
     *
     * @param name Název screenu
     * @param actionId  ID akce, na kterou se bude později reagovat
     * @param bundle Parametry, které se předají popup dialogu
     * @param x X-ová souřadnice popup dialogu
     * @param y Y-ová souřadnice popup dialogu
     */
    void showPopupForResult(final String name, final int actionId, final Bundle bundle, double x, double y);

    /**
     * Zobrazí předchozí screen.
     * Pokud předchozí screen neexistuje
     */
    void back();

    /**
     * Zobrazí předchozí screen
     *
     * @param bundle Parametry, které se předají volajícímu screenu
     * @param statusCode Status kód, podle kterého se zjistí, zda-li bylo volání úspěšné,
     *                   či nikoliv
     */
    void finish(final Bundle bundle, final int statusCode);

    /**
     * Nastaví velikost okna
     *
     * @param width Šířka okna
     * @param height Výška okna
     */
    void resize(double width, double height);

    /**
     * Nastaví název okna
     *
     * @param title Název okna
     */
    void setTitle(String title);

    /**
     * Zavře všechny screeny, které jsou spravovány tímto manažerem
     */
    void closeChildScreens();

    /**
     * Zobrazí v aktuálním screenu notifikaci
     *
     * @param notification {@link Notification}
     */
    void showNotification(Notification notification);

}