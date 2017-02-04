package cz.stechy.screens.simple.controller;

import cz.stechy.screens.BaseController;
import cz.stechy.screens.Bundle;
import javafx.event.ActionEvent;


public class Controller2 extends BaseController {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTitle("Druhy okno");
    }

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
