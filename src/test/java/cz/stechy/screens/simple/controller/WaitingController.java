package cz.stechy.screens.simple.controller;

import cz.stechy.screens.BaseController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class WaitingController extends BaseController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        Executors.newScheduledThreadPool(1).schedule(() -> Platform.runLater(this::hideWaitingScreen), 5, TimeUnit.SECONDS);
    }
}
