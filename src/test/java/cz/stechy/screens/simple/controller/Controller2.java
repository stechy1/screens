package cz.stechy.screens.simple.controller;

import cz.stechy.screens.BaseController;
import javafx.event.ActionEvent;
import javafx.scene.Node;


public class Controller2 extends BaseController {

    @Override
    protected void beforeShow() {
        setTitle("Druhy okno");
        setScreenSize(500, 600);
    }

    public void goTo1(ActionEvent actionEvent) {
        //startScreen("file1");
        startNewPopupWindow("popup", (Node) actionEvent.getSource());
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
