package com.movie_collection.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.net.URL;
import java.util.ResourceBundle;

public class MediaPlayerSelectionController extends BaseController implements Initializable {

    @FXML
    private TextField path;

    @FXML
    public Button onClickSelectFile,confirm_action,cancelAction;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //onClickSelectFile.setOnAction(this::selectFileChooser);
        //confirm_action.setOnAction(this::movieOnClickAction);
        cancelAction.setOnAction(e -> getStage().close()); // sets to close stage on action
    }


    private boolean isValidatedInput() {
        boolean isValidated = false;
        if(path.getText().isEmpty()){
            System.out.println("Please Select the path for your media player" );
        }else  {
            isValidated = true;
        }
        return isValidated;
    }
}
