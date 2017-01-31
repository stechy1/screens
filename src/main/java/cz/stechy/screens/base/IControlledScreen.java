package cz.stechy.screens.base;

/**
 * Rozhraní definující metody pro všechny screeny
 */
public interface IControlledScreen {

    /**
     * Nastaví správce screenů
     *
     * @param screenManager Správce screenů
     */
    void setScreenManager(IScreenManager screenManager);
}
