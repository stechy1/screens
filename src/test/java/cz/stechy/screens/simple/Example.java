package cz.stechy.screens.simple;

import cz.stechy.screens.ScreenManager;
import cz.stechy.screens.ScreenManagerConfiguration;
import cz.stechy.screens.base.IMainScreen;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Example extends Application {

    private static ScreenManager manager;

    private static void initScreenManager() {
        ScreenManagerConfiguration configuration = new ScreenManagerConfiguration.ConfigurationBuilder()
            .baseFxml(Example.class.getClassLoader().getResource("main.fxml"))
            .fxml(Example.class.getClassLoader().getResource("view").getPath())
            .css(Example.class.getClassLoader().getResource("css").getPath())
            .lang(Example.class.getClassLoader().getResource("lang").getPath())
            .audio(Example.class.getClassLoader().getResource("audio").getPath())
            .images(Example.class.getClassLoader().getResource("images").getPath())
            .config(Example.class.getClassLoader().getResource("config").getPath())
            .build();
        manager = new ScreenManager(configuration);
        ResourceBundle resources = ResourceBundle.getBundle("lang.translate", Locale.getDefault());
        manager.setResources(resources);
    }

    public static void main(String[] args) {
        initScreenManager();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(manager.getScreenManagerConfiguration().baseFxml);
        loader.setResources(manager.getResources());
        Parent parent = loader.load();
        IMainScreen controlledScreen = loader.getController();
        manager.setMainScreen(controlledScreen);
        manager.addScreensToBlacklist("screen4", "screen7");
        manager.loadScreens();
        manager.resize(800, 600);
        manager.setTitle(manager.getResources().getString("title"));
        manager.showNewDialog(parent, primaryStage);
        manager.showScreen("file1", null);
    }
}