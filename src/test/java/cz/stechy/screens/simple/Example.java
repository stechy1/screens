package cz.stechy.screens.simple;

import cz.stechy.screens.ScreenManager;
import cz.stechy.screens.ScreenManagerConfiguration;
import cz.stechy.screens.base.IMainScreen;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Example extends Application {

    private ScreenManager manager;

    private void initScreenManager() {
        ScreenManagerConfiguration configuration = new ScreenManagerConfiguration.Builder()
            .fxml(Example.class.getClassLoader().getResource("view"))
            .css(Example.class.getClassLoader().getResource("css/style.css"))
            .lang(Example.class.getClassLoader().getResource("lang"))
//            .audio(Example.class.getClassLoader().getResource("audio").getPath())
//            .images(Example.class.getClassLoader().getResource("images").getPath())
//            .config(Example.class.getClassLoader().getResource("config").getPath())
            .build();
        manager = new ScreenManager(configuration);
        ResourceBundle resources = ResourceBundle.getBundle("lang.translate", Locale.getDefault());
        manager.setResources(resources);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        ScreenManager.setKeyPressedHandler(event -> System.out.println("Bylo stisknuto: " + event.getText()));
        initScreenManager();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(manager.getScreenManagerConfiguration().baseFxml);
        loader.setResources(manager.getResources());
        AnchorPane parent = loader.load();
        parent.setStyle("-fx-background-color: transparent;");
        IMainScreen controlledScreen = loader.getController();
        //manager.setControllerFactory();
        manager.setMainScreen(controlledScreen);
        manager.addScreensToBlacklist("screen4", "screen7");
        manager.loadScreens();
        manager.resize(800, 600);
        manager.setTitle(manager.getResources().getString("title"));
        manager.showNewDialog(parent, primaryStage, true);
        manager.showScreen("file1", null);
    }
}