package cz.stechy.screens.simple.controller;

import cz.stechy.screens.BaseController;
import javafx.event.ActionEvent;

/**
 * Kontroler pro popup okno
 */
public class PopupController1 extends BaseController {

    @Override
    protected void onResume() {
        setScreenSize(100, 100);
    }

    public void handleClickMe(ActionEvent actionEvent) {
        finish();
    }
}
