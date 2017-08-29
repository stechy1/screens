package cz.stechy.screens.simple.controller;

import cz.stechy.screens.BaseController;
import cz.stechy.screens.Bundle;
import cz.stechy.screens.Notification;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;


public class Controller1 extends BaseController implements Initializable {

    public StackPane partContainer;
    public ToggleButton toggleButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toggleButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                getPartManager().inContainer(partContainer).onLoaded(
                    (child, controller) -> System.out.println("Loaded 2")).show("part2");
            } else {
                getPartManager().inContainer(partContainer).onLoaded(
                    (child, controller) -> System.out.println("Loaded 1")).show("part1");
            }
        });
    }

    @Override
    protected void beforeShow() {
        setTitle("Prvni okno");
        setScreenSize(500, 400);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        getPartManager().inContainer(partContainer).onLoaded(child, Object controller -> System.out.println("Loaded")).show("part1");
    }

    public void goTo2(ActionEvent actionEvent) {
        //startScreen("file2");
        startScreenForResult("file2", 1);
    }

    public void goTo3(ActionEvent actionEvent) {
        //startScreen("file3");
        startNewDialogForResult("file3", 1);
    }

    public void goBack(ActionEvent actionEvent) {
        closeChildScreens();
        back();
    }

    @Override
    public void onScreenResult(int statusCode, int actionId,
        Bundle properties) {
        switch (actionId) {
            case 1:
                System.out.println("Status code: " + statusCode);
                break;
        }
    }

    public void handleFinish(ActionEvent actionEvent) {
        setResult(RESULT_SUCCESS);
        finish();
    }

    static int id = 0;

    public void handleNotify(ActionEvent actionEvent) {
        showNotification(new Notification("Testovací notifikace: " + id++));
        //showNotification("Testovací notifikace: " + id++, Length.LONG);
    }

    @Override
    protected void onClose() {
        System.out.println("On close");
    }
}
