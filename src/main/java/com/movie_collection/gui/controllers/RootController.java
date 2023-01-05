package com.movie_collection.gui.controllers;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;

public abstract class RootController {

    private Parent parentRoot;

    public Parent getView(){
        return this.parentRoot;
    }

    public void setView(Parent parentRoot){
        this.parentRoot = parentRoot;
    }

}