package cz.stechy.screens.simple.controller;

import cz.stechy.screens.base.IMainScreen;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class MainController implements Initializable, IMainScreen {

    @FXML
    private StackPane container;
    private ObservableList<Node> containerContent;
    private final Label notification = new Label("Notification");
    private final BooleanProperty notificationVisibility = new SimpleBooleanProperty();

    {
        notification.setStyle("-fx-background-color: 'darkgrey'; -fx-label-padding: 8; -fx-background-radius: 16;");
        StackPane.setAlignment(notification, Pos.BOTTOM_CENTER);
        StackPane.setMargin(notification, new Insets(8.0));
    }

    @Override
    public void setChildNode(Node node) {
        containerContent.clear();
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 10.0);
        AnchorPane.setRightAnchor(node, 10.0);
        AnchorPane.setBottomAnchor(node, 10.0);
        containerContent.setAll(node, notification);
    }

    @Override
    public Node getContainer() {
        return container;
    }

//    @Override
//    public void showNotification(String text) {
//        notification.setText(text);
//        notificationVisibility.set(true);
//    }
//
//    @Override
//    public void hideNotification() {
//        notificationVisibility.set(false);
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        containerContent = container.getChildren();

        notification.visibleProperty().bind(notificationVisibility);
    }
}
