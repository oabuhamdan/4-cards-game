package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void aboutClicked(ActionEvent event) {

    }

    @FXML
    void closeClicked(ActionEvent event) {
        System.exit(0);

    }

    @FXML
    void signInClicked(ActionEvent event)  {
        try{
            Parent loader = FXMLLoader.load(getClass().getResource("/fxmls/signIn.fxml"));//Creates a Parent called loader and assign it as ScReen2.FXML
            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            app_stage.setScene(scene); //This sets the scene as scene
            app_stage.show(); // this shows the scene
        }
        catch (Exception e){e.printStackTrace();}
    }

    @FXML
    void signUpClicked(ActionEvent event){
        try{
            Parent loader = FXMLLoader.load(getClass().getResource("/fxmls/signUp.fxml"));//Creates a Parent called loader and assign it as ScReen2.FXML
            Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"
            Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
            app_stage.setScene(scene); //This sets the scene as scene
            app_stage.show(); // this shows the scene
        }
        catch (Exception e){e.printStackTrace();}
    }

    @FXML
    void initialize() throws IOException {
    }
}
