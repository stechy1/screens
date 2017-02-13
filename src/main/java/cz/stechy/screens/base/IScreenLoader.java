package cz.stechy.screens.base;

import cz.stechy.screens.ScreenInfo;
import java.io.IOException;
import java.util.Map;

/**
 * Rozhraní
 */
public interface IScreenLoader {

    /**
     * Načte informace o screenech
     *
     * @return Kolekci s cestami k jednotlivým screenum
     */
    Map<String, ScreenInfo> loadScreens() throws IOException;

}
