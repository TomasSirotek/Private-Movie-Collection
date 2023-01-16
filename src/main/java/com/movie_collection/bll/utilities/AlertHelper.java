package com.movie_collection.bll.utilities;

import com.movie_collection.Main;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;

import java.util.Objects;
import java.util.Optional;

public class AlertHelper {
    private static Alert alert;
    /**
     * Show custom alert box with needed description and its alert type
     * @param content that will be displayed inside the show alert as main message
     * @param type java fx scene control Alert enum type
     * @return alert that will be shown and will be waiting for user action
     */
    public static Optional<ButtonType> showOptionalAlertWindow(String title,String content, Alert.AlertType type) {
        alert = new Alert(type);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.getDialogPane().getChildren()
                .stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> ((Label)node)
                        .setMinHeight(Region.USE_PREF_SIZE));
        alert.setResizable(false);
        alert.getDialogPane().setMaxWidth(350);
        alert.initStyle(StageStyle.UNDECORATED);

        alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(Main.class.getResource("css/bootstrap3.css")).toExternalForm());
        return alert.showAndWait();
    }

    public static void showDefaultAlert(String content, Alert.AlertType type) {
        alert = new Alert(type);
        alert.setAlertType(type);
        alert.setHeaderText(null); // maybe redundant
        alert.setContentText(content);
        alert.getDialogPane().getChildren()
                .stream()
                .filter(node -> node instanceof Label)
                .forEach(node -> ((Label)node)
                        .setMinHeight(Region.USE_PREF_SIZE));
        alert.setResizable(false);
        alert.getDialogPane().setMaxWidth(350);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(Main.class.getResource("css/bootstrap3.css")).toExternalForm());
        alert.show();
    }
}
