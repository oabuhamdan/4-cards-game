package controllers;

import Objects.Challenge;
import Objects.ClientSocket;
import Objects.Utils;
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

import javax.swing.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChooseChallengeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> challengesList;

    private List<Challenge> challenges;

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
    void chatRoomClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/chatRoom.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Chat Room");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void closeClicked(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void howToPlayClicked(ActionEvent event) {

    }

    @FXML
    void leaderBoardClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/leaderBoard.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Leader Board");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void playButtonClicked(ActionEvent event) throws IOException {
        String value = challengesList.getSelectionModel().getSelectedItem();
        int index = Integer.parseInt(value.substring(11, 13).replace(" ", ""));

        ChallengeViewerController temp;
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
    void signOutClicked(ActionEvent event) throws IOException {
        /*Parent loader = FXMLLoader.load(getClass().getResource("/fxmls/startPage.fxml"));//Creates a Parent called loader and assign it as ScReen2.FXML
        Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
        app_stage.setScene(scene); //This sets the scene as scene
        app_stage.show(); // this shows the scene*/
        JOptionPane.showMessageDialog(null, "You are signed out");
        ClientSocket.terminateConnection();
    }

    @FXML
    void initialize() {
        ClientSocket.createNewConnection();
        assert challengesList != null : "fx:id=\"challengesList\" was not injected: check your FXML file 'createChallenge.fxml'.";
        List<String> challengesDesc = new ArrayList<>();
        challenges = loadChallenges();
        Utils.setChallenges(challenges);
        for ( int i = 0; i < challenges.size(); i++ ) {
            challengesDesc.add("Challenge #" + i + " \t\t\t "
                    + challenges.get(i).getTime() + " Minutes" + "\t\t" + challenges.get(i).getCreator());
        }
        if (challenges.size() > 0) {
            ObservableList<String> observableList = FXCollections.observableArrayList(challengesDesc);
            FilteredList<String> filteredList = new FilteredList<>(observableList, s -> true);
            challengesList.setItems(filteredList);
        }
    }

    private List<Challenge> loadChallenges() {
        List<Challenge> challenges = null;
        try {
            Socket socket = ClientSocket.getInstance();
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("getChallenges");
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            challenges = (List<Challenge>) ois.readObject();
            Utils.setChallenges(challenges);
        } catch (Exception e) {
        }
        return challenges;
    }
}

