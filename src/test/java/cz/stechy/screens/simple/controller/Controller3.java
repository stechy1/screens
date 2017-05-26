package cz.stechy.screens.simple.controller;

import cz.stechy.screens.BaseController;
import javafx.event.ActionEvent;


public class Controller3 extends BaseController {

    @Override
    protected void beforeShow() {
        setTitle("Treti okno");
        setScreenSize(300, 500);
    }

    public void goTo1(ActionEvent actionEvent) {
        startScreen("file1");
    }

    public void goTo2(ActionEvent actionEvent) {
        startScreen("file2");
    }

    public void goBack(ActionEvent actionEvent) {
        back();
    }

    public void handleFinish(ActionEvent actionEvent) {
        setResult(RESULT_SUCCESS);
        finish();
    }
}
