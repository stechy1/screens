package cz.stechy.screens;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * Třída reprezentující správce kousků screenu.
 * Umožňuje zobrazovat kousky screenu v rodičovské obrazovce
 */
public final class ScreenPartManager {

    // region Variables

    private final ScreenManager screenManager;

    private Pane container;
    private OnLoadListener listener;

    // endregion

    // region Constructors

    ScreenPartManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    // endregion

    // region Public methods

    /**
     * Nastaví kontejner, ve kterém se bude zobrazovat potomek
     *
     * @param container Rodičovsk kontejner
     * @return {@link ScreenPartManager}
     */
    public ScreenPartManager inContainer(final Pane container) {
        this.container = container;

        return this;
    }

    /**
     * Nastaví listener, který se zavolá po úspěšném načtení potomka
     *
     * @param listener {@link OnLoadListener}
     * @return {@link ScreenPartManager}
     */
    public ScreenPartManager onLoaded(OnLoadListener listener) {
        this.listener = listener;

        return this;
    }

    /**
     * Načte a zobrazí potomka v rodičovském kontejneru
     *
     * @param name Název screenu potomka
     */
    public void show(final String name) {
        if (container == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(screenManager.getScreenUrl(name));
        loader.setResources(screenManager.getResources());
        loader.setControllerFactory(screenManager.getFactory());
        loader.setBuilderFactory(screenManager.getBuilderFactory());
        try {
            final Node node = loader.load();
            container.getChildren().setAll(node);

            if (listener != null) {
                listener.onLoad(node, loader.getController());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // endregion

    @FunctionalInterface
    public interface OnLoadListener {

        /**
         * Metoda se zavolá po načtení potomka
         *
         * @param child Potomek, který se vložil do kontejneru
         * @param controller Kontroler potomka
         */
        void onLoad(Node child, Object controller);
    }

}
