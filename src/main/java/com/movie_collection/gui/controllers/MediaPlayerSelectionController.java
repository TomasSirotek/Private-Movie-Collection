package com.movie_collection.gui.controllers;

import com.google.inject.Inject;
import com.movie_collection.gui.controllers.abstractController.RootController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private Button onClickSelectFile,confirm_action,cancelAction;

    private final MovieController movieController;

    @Inject
    public MediaPlayerSelectionController(MovieController movieController) {
        this.movieController = movieController;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onClickSelectFile.setOnAction(this::selectFileChooser);
        confirm_action.setOnAction(this::movieOnClickAction);
        cancelAction.setOnAction(e -> getStage().close());
    }

    private void movieOnClickAction(ActionEvent actionEvent) {
        if(!path.getText().isEmpty()){
            getStage().close();
            System.out.println("Successfully");
        }else {
            System.out.println("bleh");
        }
    }

    private void selectFileChooser(ActionEvent actionEvent) {
        var chooseFile = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SoundFiles files (*.exe)", "*.exe");
        chooseFile.getExtensionFilters().add(extFilter);

        File selectedExecutableFile = chooseFile.showOpenDialog(new Stage());
        if (selectedExecutableFile != null) {
            path.setText(selectedExecutableFile.toURI().toString());
            String mediaPlayerPath = selectedExecutableFile.toString();
            File txt = new File("mediaPlayerPath.txt");
            Path fileName = Path.of(txt.getAbsolutePath());
            movieController.setPath(fileName, mediaPlayerPath);

        }
    }
}
