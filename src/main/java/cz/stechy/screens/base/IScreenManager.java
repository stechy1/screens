package cz.stechy.screens.base;

import cz.stechy.screens.Bundle;

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
        showScreenForResult(name, NO_ACTION, new Bundle());
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
        showDialogForResult(name, NO_ACTION, new Bundle());
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
}