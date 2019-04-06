package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField passowrdField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField userNameField;

    @FXML
    void aboutClicked(ActionEvent event) {
    }

    @FXML
    void addNewUserButtonClicked(ActionEvent event) {
        System.out.println(userNameField.getText());
        System.out.println(passowrdField.getText());
        System.out.println(emailField.getText());
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
    void closeClicked(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void initialize() {
        assert passowrdField != null : "fx:id=\"passowrdField\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert emailField != null : "fx:id=\"emailField\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert userNameField != null : "fx:id=\"userNameField\" was not injected: check your FXML file 'SignUp.fxml'.";

    }
}
