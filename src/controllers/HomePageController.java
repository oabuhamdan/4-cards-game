package controllers;

import Objects.ClientSocket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void aboutClicked(ActionEvent event) {

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
    void closeClicked(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void createChallengeButtonClicked(ActionEvent event) throws IOException {
        Parent loader = FXMLLoader.load(getClass().getResource("/fxmls/createChallenge.fxml"));//Creates a Parent called loader and assign it as ScReen2.FXML
        Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
        app_stage.setScene(scene); //This sets the scene as scene
        app_stage.show(); // this shows the scene
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
        Parent loader = FXMLLoader.load(getClass().getResource("/fxmls/chooseChallenge.fxml"));//Creates a Parent called loader and assign it as ScReen2.FXML
        Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
        app_stage.setScene(scene); //This sets the scene as scene
        app_stage.show(); // this shows the scene
    }

    @FXML
    void initialize() {

    }
}
