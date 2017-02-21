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

package cz.stechy.screens;

import cz.stechy.screens.base.IControlledScreen;
import cz.stechy.screens.base.IScreenManager;
import javafx.scene.Node;

/**
 * Základní třída každého screenu
 */
public abstract class BaseController implements IControlledScreen {

    // Příznak pro úspěšnou akci
    public static final int RESULT_FAIL = 0;
    // Příznak pro neúspěšnou akci
    public static final int RESULT_SUCCESS = 1;

    // Screen manager
    private IScreenManager mManager;
    // Výsledek akce
    private int mResult = RESULT_FAIL;

    /**
     * Metoda je zavolána, když se poprve zobrazí tento screen
     *
     * @param bundle {@link Bundle} Parametry předané rodičovským screenem
     */
    protected void onCreate(final Bundle bundle) {

    }

    /**
     * Metoda je zavolána pokažde, když se zobrazí screen
     */
    protected void onResume() {

    }

    /**
     * Matoda je zavolána, když se úspěšně zavře dialog
     *
     * @param statusCode Status kód říkající, zda-li byla akce úspěšná, či nikoliv
     * @param actionId Id akce, která byla vykonána
     * @param bundle {@link Bundle} Parametry předané z dialogu
     */
    protected void onScreenResult(final int statusCode, final int actionId,
        final Bundle bundle) {

    }

    /**
     * Metoda je zavolána, když tento screen končí
     */
    protected void onClose() {

    }

    /**
     * Zobrazí nový screen a vloží ho na stack
     *
     * @param name Název FXML souboru definující screen
     */
    protected void startScreen(final String name) {
        mManager.showScreen(name, new Bundle());
    }

    /**
     * Zobrazí nový screen a vloží ho na stack
     *
     * @param name Název FXML souboru definující screen
     * @param bundle {@link Bundle} Parametry, které se předají dalšímu screenu
     */
    protected void startScreen(final String name, final Bundle bundle) {
        mManager.showScreen(name, bundle);
    }

    /**
     * Zobrazí nový screen s tím, že se očekává výsledek
     *
     * @param name Název FXML souboru definující screen
     * @param actionId Id akce, na kterou se pak bude reagovat
     */
    protected void startScreenForResult(final String name, final int actionId) {
        mManager.showScreenForResult(name, actionId, new Bundle());
    }

    /**
     * Zobrazí nový screen s tím, že se očekává výsledek
     *
     * @param name Název FXML souboru definující screen
     * @param actionId Id akce, na kterou se pak bude reagovat
     * @param bundle {@link Bundle} Parametry, které se předají dalšímu screenu
     */
    protected void startScreenForResult(final String name, final int actionId,
        final Bundle bundle) {
        mManager.showScreenForResult(name, actionId, bundle);
    }

    /**
     * Zobrazí nový dialog
     *
     * @param name Název FXML souboru definující screen
     */
    protected void startNewDialog(final String name) {
        mManager.showDialog(name, new Bundle());
    }

    /**
     * Zobrazí nový dialog
     *
     * @param name Název FXML souboru definující screen
     * @param bundle {@link Bundle} Parametry, které se předají dalšímu screenu
     */
    protected void startNewDialog(final String name, final Bundle bundle) {
        mManager.showDialog(name, bundle);
    }

    /**
     * Zobrazí nový dialog s tím, že se očekává výsledek
     *
     * @param name Název FXML souboru definující screen
     * @param actionId Id akce, na kterou se pak bude reagovat
     */
    protected void startNewDialogForResult(final String name, final int actionId) {
        startNewDialogForResult(name, actionId, new Bundle());
    }

    /**
     * Zobrazí nový dialog s tím, že se očekává výsledek
     *
     * @param name Název FXML souboru definující screen
     * @param actionId Id akce, na kterou se pak bude reagovat
     * @param bundle {@link Bundle} Parametry, které se předají dalšímu screenu
     */
    protected void startNewDialogForResult(final String name, final int actionId, final Bundle bundle) {
        mManager.showDialogForResult(name, actionId, bundle);
    }

    /**
     * Zobrazí popup dialog na pozici získané z rodičovského prvku
     *
     * @param name Název screenu
     * @param parentNode Rodičovský node, ke kterému se dialog "připne"
     */
    protected void startNewPopupWindow(final String name, Node parentNode) {
        mManager.showPopup(name, new Bundle(), parentNode);
    }

    /**
     * Zobrazí popup dialog na pozici získané z rodičovského prvku
     *
     * @param name Název screenu
     * @param x X-ová souřadnice popup dialogu
     * @param y Y-ová souřadnice popup dialogu
     */
    protected void startNewPopupWindow(final String name, double x, double y) {
        mManager.showPopup(name, new Bundle(), x, y);
    }

    /**
     * Zobrazí popup dialog na pozici získané z rodičovského prvku
     *
     * @param name Název screenu
     * @param bundle Parametry, které se předají popup dialogu
     * @param parentNode Rodičovský node, ke kterému se dialog "připne"
     */
    protected void startNewPopupWindow(final String name, final Bundle bundle, Node parentNode) {
        mManager.showPopup(name, bundle, parentNode);
    }

    /**
     * Zobrazí popup dialog na pozici získané z rodičovského prvku
     *
     * @param name Název screenu
     * @param bundle Parametry, které se předají popup dialogu
     * @param x X-ová souřadnice popup dialogu
     * @param y Y-ová souřadnice popup dialogu
     */
    protected void startNewPopupWindow(final String name, final Bundle bundle, double x, double y) {
        mManager.showPopup(name, bundle, x, y);
    }

    /**
     * Zobrazí popup dialog na pozici získané z rodičovského prvku
     *
     * @param name Název screenu
     * @param actionId  ID akce, na kterou se bude později reagovat
     * @param parentNode Rodičovský node, ke kterému se dialog "připne"
     */
    protected void startNewPopupWindowForResult(final String name, final int actionId, final Node parentNode) {
        mManager.showPopupForResult(name, actionId, new Bundle(), parentNode);
    }

    /**
     * Zobrazí popup dialog na pozici získané z rodičovského prvku
     *
     * @param name Název screenu
     * @param actionId  ID akce, na kterou se bude později reagovat
     * @param x X-ová souřadnice popup dialogu
     * @param y Y-ová souřadnice popup dialogu
     */
    protected void startNewPopupWindowForResult(final String name, final int actionId, double x, double y) {
        mManager.showPopupForResult(name, actionId, new Bundle(), x, y);
    }

    /**
     * Zobrazí popup dialog na pozici získané z rodičovského prvku
     *
     * @param name Název screenu
     * @param actionId  ID akce, na kterou se bude později reagovat
     * @param bundle Parametry, které se předají popup dialogu
     * @param parentNode Rodičovský node, ke kterému se dialog "připne"
     */
    protected void startNewPopupWindowForResult(final String name, final int actionId, final Bundle bundle, Node parentNode) {
        mManager.showPopupForResult(name, actionId, bundle, parentNode);
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
    protected void startNewPopupWindowForResult(final String name, final int actionId, final Bundle bundle, double x, double y) {
        mManager.showPopupForResult(name, actionId, bundle, x, y);
    }

    /**
     * Nastaví výsledek akce
     *
     * @param result Výsledek akce
     */
    protected void setResult(final int result) {
        mResult = result;
    }

    /**
     * Zobrazí předchozí screen. Pokud předchozí screen neexistuje
     * a jedná se o dialog, tak se dialog zavře
     */
    protected void back() {
        mManager.back();
    }

    /**
     * Pokud se jedná o dialog, tak se dialog zavře
     * a odešle se informace o výsledku rodičovskému screenu
     */
    protected void finish() {
        finish(new Bundle());
    }

    /**
     * Pokud se jedná o dialog, tak se dialog zavře
     * a odešle se informace o výsledku rodičovskému screenu
     *
     * @param bundle {@link Bundle} Parametry, které se předají rodičoskému screenu
     */
    protected void finish(final Bundle bundle) {
        mManager.finish(bundle, mResult);
    }

    /**
     * Nastaví velikost okna
     *
     * @param width Šířka okna
     * @param height Výška okna
     */
    protected void setScreenSize(double width, double height) {
        mManager.resize(width, height);
    }

    /**
     * Nastaví název okna
     *
     * @param title Název okna
     */
    protected void setTitle(String title) {
        mManager.setTitle(title);
    }

    /**
     * Zavře všechny screeny vyvolané z tohoto dialogu
     */
    protected void closeChildScreens() {
        mManager.closeChildScreens();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setScreenManager(final IScreenManager screenManager) {
        mManager = screenManager;
    }
}
