package cz.stechy.screens;

import cz.stechy.screens.base.IScreenLoader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Standartná načítač screenů
 */
public class SimpleScreenLoader implements IScreenLoader {

    private final File rootDirectory;
    private final List<String> blacklist;
    final Map<String, ScreenInfo> screens = new HashMap<>();

    public SimpleScreenLoader(URL rootDirectoryUrl, List<String> blacklist) {
        rootDirectory = new File(rootDirectoryUrl.getPath());
        this.blacklist = blacklist;
    }

    @Override
    public Map<String, ScreenInfo> loadScreens() throws IOException {
        loadScreensInternal(rootDirectory);
        return screens;
    }

    private void loadScreensInternal(File directory) throws IOException {
        final File[] views = directory.listFiles();
        for (File entry : views) {
            if (entry.isDirectory()) {
                loadScreensInternal(entry);
            } else {
                final String name = entry.getName();
                final int dotIndex = name.indexOf(".");
                final String clearName = name
                    .substring(0, dotIndex > -1 ? dotIndex : name.length());
                if (blacklist.contains(clearName)) {
                    continue;
                }
                screens.put(clearName, new ScreenInfo(clearName, entry.toURI().toURL()));
            }
        }
    }

}
