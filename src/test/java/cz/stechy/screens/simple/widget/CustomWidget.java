package cz.stechy.screens.simple.widget;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CustomWidget extends VBox {

    private final Label lblTitle = new Label("Test");
    private final TextField txtInput = new TextField();

    public CustomWidget(String title) {
        super();
        lblTitle.setText(title);
        System.out.println("ctor with title");
        getChildren().setAll(lblTitle, txtInput);
    }

}
