package cz.stechy.screens.simple.controller;

import cz.stechy.screens.BaseController;
import cz.stechy.screens.Bundle;
import cz.stechy.screens.Notification;
import javafx.event.ActionEvent;


public class Controller1 extends BaseController {

    @Override
    protected void beforeShow() {
        setTitle("Prvni okno");
        setScreenSize(500, 400);
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
}
