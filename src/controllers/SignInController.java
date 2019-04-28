package controllers;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import Objects.ClientSocket;
import Objects.LocalDataHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;

public class SignInController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userNameField;

    private Socket socket;

    @FXML
    void aboutClicked(ActionEvent event) {

    }

    @FXML
    void clearFieldsButtonClicked(ActionEvent event) {

    }

    @FXML
    void closeClicked(ActionEvent event) {

    }

    @FXML
    void signInButtonClicked(ActionEvent event) throws IOException {
        Parent loader;
        if (checkCardinality()) {
            loader = FXMLLoader.load(getClass().getResource("/fxmls/homePage.fxml"));
            LocalDataHandler.setSignedInUser(userNameField.getText());
        } else {
            JOptionPane.showMessageDialog(null, "User name or Password is wrong\nPlease try again");
            loader = FXMLLoader.load(getClass().getResource("/fxmls/signIn.fxml"));//Creates a Parent called loader and assign it as ScReen2.FXML
        }
        Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
        app_stage.setScene(scene); //This sets the scene as scene
        app_stage.show(); // this
    }


    @FXML
    void backButtonClicked(ActionEvent event) throws IOException {
        Parent loader = FXMLLoader.load(getClass().getResource("/fxmls/startPage.fxml"));//Creates a Parent called loader and assign it as ScReen2.FXML
        Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
        app_stage.setScene(scene); //This sets the scene as scene
        app_stage.show(); // this shows the scene
    }

    @FXML
    void initialize() {
        ClientSocket.createNewConnection();
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'SignIn.fxml'.";
        assert userNameField != null : "fx:id=\"userNameField\" was not injected: check your FXML file 'SignIn.fxml'.";

    }

    private boolean checkCardinality() {
        String response = "false";
       /*try {
            Socket socket = ClientSocket.getInstance();
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            dos.writeUTF("checkCardinality");
            dos.writeUTF(userNameField.getText());
            dos.writeUTF(passwordField.getText());
            response = dis.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return !response.equals("true");
    }
}
