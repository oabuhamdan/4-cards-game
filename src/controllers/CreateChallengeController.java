package controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CreateChallengeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<?> photosNamesTable;

    @FXML
    private TextField relatedWord;

    @FXML
    void aboutClicked(ActionEvent event) {

    }

    @FXML
    void backButtonClicked(ActionEvent event) {

    }

    @FXML
    void browseButtonClicked(ActionEvent event) {

    }

    @FXML
    void chatRoomClicked(ActionEvent event) {

    }

    @FXML
    void closeClicked(ActionEvent event) {

    }

    @FXML
    void howToPlayClicked(ActionEvent event) {

    }

    @FXML
    void leaderBoardClicked(ActionEvent event) {

    }

    @FXML
    void signOutClicked(ActionEvent event) {

    }

    @FXML
    void submitButtonClicked(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert photosNamesTable != null : "fx:id=\"photosNamesTable\" was not injected: check your FXML file 'Start Page.fxml'.";
        assert relatedWord != null : "fx:id=\"relatedWord\" was not injected: check your FXML file 'Start Page.fxml'.";

    }
}
