package controllers;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import Objects.ClientSocket;
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
    private PasswordField passowrdField;

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
        if (checkCardinality()) {
            Parent loader = FXMLLoader.load(getClass().getResource("/fxmls/homePage.fxml"));//Creates a Parent called loader and assign it as ScReen2.FXML
            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            app_stage.setScene(scene); //This sets the scene as scene
            app_stage.show(); // this shows the scene
        } else {
            JOptionPane.showMessageDialog(null, "User name or Password is wrong\nPlease try again");
        }
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
        assert passowrdField != null : "fx:id=\"passowrdField\" was not injected: check your FXML file 'SignIn.fxml'.";
        assert userNameField != null : "fx:id=\"userNameField\" was not injected: check your FXML file 'SignIn.fxml'.";

    }

    private boolean checkCardinality() {
        String response = "true";
        /*try {
            Socket socket = ClientSocket.getInstance();
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
            DataOutputStream dos = new DataOutputStream(bos);
            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
            DataInputStream dis = new DataInputStream(bis);
            dos.writeUTF("checkCardinality");
            dos.writeUTF(userNameField.getText());
            dos.writeUTF(passowrdField.getText());
            response = dis.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return response.equals("true");
    }
}
