package cz.stechy.screens.simple.controller;

import cz.stechy.screens.BaseController;
import cz.stechy.screens.Bundle;
import javafx.event.ActionEvent;


public class Controller1 extends BaseController {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTitle("Prvni okno");
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
}
