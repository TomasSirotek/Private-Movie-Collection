package com.movie_collection.gui.controllers;

import com.google.inject.Inject;
import com.movie_collection.bll.utilities.AlertHelper;
import com.movie_collection.gui.controllers.abstractController.RootController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class MediaPlayerSelectionController extends RootController implements Initializable {

    @FXML
    private TextField path;

    @FXML
    private Button onClickSelectFile, confirmAction,cancelAction;

    private final MovieController movieController;

    private final static String MEDIA_PLAYER_PATH = "mediaPlayerPath.txt";

    @Inject
    public MediaPlayerSelectionController(MovieController movieController) {
        this.movieController = movieController;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onClickSelectFile.setOnAction(this::selectFileChooser);
        confirmAction.setOnAction(this::setConfirmAction);
        cancelAction.setOnAction(e -> getStage().close());
    }

    private void setConfirmAction(ActionEvent actionEvent) {
        if(!path.getText().isEmpty()){
            getStage().close();
        }else {
            AlertHelper.showDefaultAlert("Warning: Please fill the path", Alert.AlertType.WARNING);
        }
    }

    private void selectFileChooser(ActionEvent actionEvent) {
        FileChooser chooseFile = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("App files (*.exe,*.app)", "*.exe","*.app");
        chooseFile.getExtensionFilters().add(extFilter);

        File selectedExecutableFile = chooseFile.showOpenDialog(new Stage());
        if (selectedExecutableFile != null) {
            String absolutePath = selectedExecutableFile.getAbsolutePath();
            path.setText(absolutePath);
            try {
                Files.writeString(Path.of(MEDIA_PLAYER_PATH), absolutePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
