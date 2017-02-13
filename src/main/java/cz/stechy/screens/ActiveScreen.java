package cz.stechy.screens;

/**
 * Created by petr on 13.2.17.
 */
public class ActiveScreen {

    final int actionId;
    final ScreenInfo screenInfo;
    final ActiveScreen parent;

    /**
     * Vytvoří nový záznam o aktivním screenu, který nemá rodiče
     *
     * @param actionId Id akce
     * @param screenInfo {@link ScreenInfo}
     */
    ActiveScreen(final int actionId, final ScreenInfo screenInfo) {
        this(actionId, screenInfo, null);
    }

    /**
     * Vytvoří nový záznam o aktivním screenu, který má rodiče
     *
     * @param actionId Id akce
     * @param screenInfo {@link ScreenInfo}
     * @param parent Rodič screenu {@link ActiveScreen}
     */
    ActiveScreen(final int actionId, final ScreenInfo screenInfo, ActiveScreen parent) {
        this.actionId = actionId;
        this.screenInfo = screenInfo;
        this.parent = parent;
    }
}
