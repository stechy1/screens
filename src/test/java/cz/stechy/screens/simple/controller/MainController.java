package cz.stechy.screens.simple.controller;

import cz.stechy.screens.base.IMainScreen;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class MainController implements Initializable, IMainScreen {

    @FXML
    private AnchorPane container;
    private ObservableList<Node> containerContent;

    @Override
    public void setChildNode(Node node) {
        containerContent.clear();
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 10.0);
        AnchorPane.setRightAnchor(node, 10.0);
        AnchorPane.setBottomAnchor(node, 10.0);
        containerContent.setAll(node);
    }

    @Override
    public Node getContainer() {
        return container;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        containerContent = container.getChildren();
    }
}
