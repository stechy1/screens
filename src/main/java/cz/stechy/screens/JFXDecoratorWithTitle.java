package cz.stechy.screens;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import java.util.Optional;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

class JFXDecoratorWithTitle extends JFXDecorator {


    // region Constructors

    JFXDecoratorWithTitle(Stage stage, Node node) {
        this(stage, node, true, true, true);
    }

    JFXDecoratorWithTitle(Stage stage, Node node, boolean fullScreen, boolean max, boolean min) {
        super(stage, node, fullScreen, max, min);
        stage.initStyle(StageStyle.TRANSPARENT);
        Optional<Node> btnContainerOpt = this.getChildren().stream()
            .filter(child ->
                child.getStyleClass().contains("jfx-decorator-buttons-container")).findFirst();
        if (btnContainerOpt.isPresent()) {
            final HBox buttonsContainer = (HBox) btnContainerOpt.get();

            final ObservableList<Node> buttons = buttonsContainer.getChildren();
            int btnMaxIdx = 0;
            if (fullScreen) {
                btnMaxIdx++;
            }
            if (min) {
                btnMaxIdx++;
            }
            if (buttons.size() >= btnMaxIdx) {
                // Handler pro double click pro zvětšení okna
                final JFXButton btnMax = (JFXButton) buttons.get(btnMaxIdx);
                buttonsContainer.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2) {
                        btnMax.fire();
                    }
                });
            }

            JFXButton btnClose = (JFXButton) buttons.get(buttons.size() - 1);
            btnClose.setOnAction(event -> {
                stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            });

            final HBox leftBox = new HBox();
            leftBox.setAlignment(Pos.CENTER_LEFT);
            leftBox.setPadding(new Insets(0, 0, 0, 10));
            leftBox.setSpacing(10);

            final HBox iconBox = new HBox();
            iconBox.setAlignment(Pos.CENTER_LEFT);
            iconBox.setSpacing(5);

            // bind icon
            stage.getIcons().addListener((ListChangeListener<Image>) c -> {
                while (c.next()) {
                    iconBox.getChildren().clear();
                    final ObservableList<? extends Image> icons = c.getList();
                        for (Image icon : icons) {
                            final ImageView imageView = new ImageView();
                            imageView.setFitWidth(20);
                            imageView.setFitHeight(20);
                            imageView.setImage(icon);
                            iconBox.getChildren().add(imageView);
                    }
                }
            });

            // bind title
            final Label title = new Label();
            title.textProperty().bindBidirectional(stage.titleProperty());
            title.setTextFill(Paint.valueOf("#fdfdfd"));

            leftBox.getChildren().addAll(iconBox, title);

            HBox.setHgrow(leftBox, Priority.ALWAYS);
            buttonsContainer.getChildren().add(0, leftBox);
        }
    }

    // endregion

    // region Public methods



    // endregion

}
