package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Objects.Challenge;
import Objects.Server;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ChooseChallengeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> challengesList;

    @FXML
    void aboutClicked(ActionEvent event) {

    }

    @FXML
    void backButtonClicked(ActionEvent event) throws IOException {
        Parent loader = FXMLLoader.load(getClass().getResource("/fxmls/homePage.fxml"));//Creates a Parent called loader and assign it as ScReen2.FXML
        Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
        app_stage.setScene(scene); //This sets the scene as scene
        app_stage.show(); // this shows the scene
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
    void playButtonClicked(ActionEvent event) throws IOException {
        String value = challengesList.getSelectionModel().getSelectedItem();
        int index = Integer.parseInt(value.substring(11, 13).replace(" ",""));
        Challenge challenge=Server.getChallenges().get(index);

        ChallengeViewerController temp ;

        //Parent loader = FXMLLoader.load(getClass().getResource("/fxmls/challengeViewer.fxml"));//Creates a Parent called loader and assign it as ScReen2.FXML

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/challengeViewer.fxml"));//Creates a Parent called loader and assign it as ScReen2.FXML
        Parent loader = fxmlLoader.load();
        temp = fxmlLoader.getController();
        temp.setSelectedChallenge(index);

        Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
        app_stage.setScene(scene); //This sets the scene as scene
        app_stage.show(); // this shows the scene

    }

    @FXML
    void signOutClicked(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert challengesList != null : "fx:id=\"challengesList\" was not injected: check your FXML file 'createChallenge.fxml'.";

        List<String> challengesDesc = new ArrayList<>();
        List<Challenge> challenges = Server.getChallenges();
        for ( int i = 0; i < challenges.size(); i++ ) {
            challengesDesc.add("Challenge #" + i + " \t\t\t\t\t\t " + challenges.get(i).getTime() + " Minutes");
        }
        if (challenges.size() > 0) {
            ObservableList<String> observableList = FXCollections.observableArrayList(challengesDesc);
            FilteredList<String> filteredList = new FilteredList<>(observableList, s -> true);
            challengesList.setItems(filteredList);
        }
    }
}
