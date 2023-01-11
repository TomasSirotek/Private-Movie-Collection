package com.movie_collection.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MediaPlayerSelectionController extends BaseController implements Initializable {

    @FXML
    private TextField path;

    @FXML
    public Button onClickSelectFile,confirm_action,cancelAction;

}
