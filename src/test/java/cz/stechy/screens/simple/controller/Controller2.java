package cz.stechy.screens.simple.controller;

import cz.stechy.screens.BaseController;
import javafx.event.ActionEvent;


public class Controller2 extends BaseController {


    public void goTo1(ActionEvent actionEvent) {
        startScreen("file1");
    }

    public void goTo3(ActionEvent actionEvent) {
        startScreen("file3");
    }

    public void goBack(ActionEvent actionEvent) {
        back();
    }

    public void handleFinish(ActionEvent actionEvent) {
        setResult(RESULT_SUCCESS);
        finish();
    }
}
