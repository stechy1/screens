package cz.stechy.screens;

import cz.stechy.screens.base.IMainScreen;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class MainScreen implements IMainScreen {

    // region Variables

    // region FXML

    @FXML
    private StackPane container;

    // endregion

    // endregion

    @Override
    public void setChildNode(Node node) {
        container.getChildren().clear();
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 10.0);
        AnchorPane.setRightAnchor(node, 10.0);
        AnchorPane.setBottomAnchor(node, 10.0);
        container.getChildren().setAll(node);
    }

    @Override
    public void addChildNode(Node node) {
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 10.0);
        AnchorPane.setRightAnchor(node, 10.0);
        AnchorPane.setBottomAnchor(node, 10.0);
        container.getChildren().add(node);
    }

    @Override
    public void removeChildNode(Node node) {
        container.getChildren().remove(node);
    }

    @Override
    public void disableScreen() {
        container.getChildren().forEach(node -> node.setDisable(true));
    }

    @Override
    public void enableScreen() {
        container.getChildren().forEach(node -> node.setDisable(false));
    }

    @Override
    public Node getContainer() {
        return container;
    }
}
