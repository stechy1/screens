package cz.stechy.screens.loader;

import cz.stechy.screens.ScreenInfo;
import cz.stechy.screens.base.IScreenLoader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Screen loader, který načítá screeny ze zazipované složky, případně jaru
 */
public class ZipScreenLoader implements IScreenLoader {

    private URI rootDirectory;
    private final List<String> blacklist;
    Map<String, ScreenInfo> screens = new HashMap<>();

    public ZipScreenLoader(URL rootDirectoryUrl, List<String> blacklist) {
        try {
            rootDirectory = rootDirectoryUrl.toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        this.blacklist = blacklist;
    }

    @Override
    public Map<String, ScreenInfo> loadScreens() {
        String path = rootDirectory.toString();
        int delimiterIndex = path.indexOf("!");
        String zipFilePath = path.substring(path.indexOf("/"), delimiterIndex);
        path = path.substring(0, path.indexOf("!/") + 2);
        try (JarFile jarFile = new JarFile(zipFilePath)) {
            for (Enumeration<JarEntry> em = jarFile.entries(); em.hasMoreElements();) {
                JarEntry entry = em.nextElement();
                String name = entry.getName();
                if (!name.endsWith(".fxml")) {
                    continue;
                }

                final int dotIndex = name.indexOf(".");
                final String clearName = name.substring(name.lastIndexOf("/") + 1, dotIndex > -1
                    ? dotIndex
                    : name.length());
                URL url = new URL(path + name);
                if (blacklist.contains(clearName)) {
                    continue;
                }
                screens.put(clearName, new ScreenInfo(clearName, url));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return screens;
    }
}
