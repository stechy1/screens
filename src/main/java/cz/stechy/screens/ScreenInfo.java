package cz.stechy.screens;

import java.net.URL;
import javafx.scene.Node;

/**
 * Created by petr on 13.2.17.
 */
public class ScreenInfo {

    // Název screenu
    final String name;
    // Cesta ke screenu
    final URL url;
    // Kořenový prvek screenu
    Node node;
    // Kontroler screenu
    BaseController controller;
    // Příznak, je-li screen načtený
    boolean loaded;

    /**
     * Vytvoří novou přepravku
     *
     * @param name Název screenu
     * @param url Soubor se screenem
     */
    public ScreenInfo(final String name, URL url) {
        this.name = name;
        this.url = url;
        node = null;
        controller = null;
        loaded = false;
    }

    void setLoaded() {
        this.loaded = true;
    }

    @Override
    public String toString() {
        return name;
    }
}
