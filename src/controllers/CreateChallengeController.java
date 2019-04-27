package controllers;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Objects.Challenge;
import Objects.ClientSocket;
import Objects.LocalDataHandler;
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
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;

public class CreateChallengeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField relatedWord;

    @FXML
    private TextField time;

    List<File> files = new ArrayList<>();

    @FXML
    private ListView<String> filesList;


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
    void browseButtonClicked(ActionEvent event) {
        /*JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Choose 4 photos");
        chooser.setFileFilter(new FileNameExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        files = Arrays.asList(chooser.getSelectedFiles());
        viewFilesNames(files);*/
        while (files.size() != 4) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose 4 photos");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg","*.JPG","*.JPEG","*.PNG"));
            files = fileChooser.showOpenMultipleDialog(new Stage());
            if (files.size() == 4)
                viewFilesNames(files);
            else {
                JOptionPane.showMessageDialog(null, "make sure to select 4 photos");
                files = new ArrayList<>();
            }
        }
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
    void submitButtonClicked(ActionEvent event) throws IOException {
        if (relatedWord.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "please insert a related word");
        } else if (time.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "please insert a time");
        } else {
            Challenge challenge = new Challenge(time.getText(), relatedWord.getText(), files);
            //TODO sendToServer(); send the 4 photos,relatedWord and time to the server and the server will manually create a challenge with the received data..

            LocalDataHandler.addChallenge(challenge);// adds the challenge to the list of LocalDataHandler.challenges locally in the client's device
            //serialization should be replaced here...
            OutputStream outputStream = new FileOutputStream("LocalDataHandler.challenges.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(LocalDataHandler.getChallenges());
            uploadFilesToServer();
            JOptionPane.showMessageDialog(null, " Challenge submitted successfully\nPress back or create new Challenge");
            relatedWord.clear();
            time.clear();
            filesList.setItems(null);

        }
    }


    @FXML
    void initialize() {
        assert relatedWord != null : "fx:id=\"relatedWord\" was not injected: check your FXML file 'uploadPhotos.fxml'.";
        assert filesList != null : "fx:id=\"filesList\" was not injected: check your FXML file 'uploadPhotos.fxml'.";

    }

    private void viewFilesNames(List<File> files) {
        List<String> fileNames = new ArrayList<>();
        for ( File file : files ) {
            fileNames.add(file.getName());
        }
        ObservableList<String> observableList = FXCollections.observableArrayList(fileNames);
        FilteredList<String> filteredList = new FilteredList<>(observableList, s -> true);
        filesList.setItems(filteredList);
    }

    private void uploadFilesToServer() {
        try {
            Socket socket = ClientSocket.getInstance();
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
            DataOutputStream dos = new DataOutputStream(bos);
            dos.writeUTF("uploadImages");
            for ( File file : files ) {
                long length = file.length();
                dos.writeLong(length);
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                int theByte;
                while ((theByte = bis.read()) != -1) bos.write(theByte);
                bis.close();
            }
        } catch (Exception e) {
        }
    }
        /*try {
            Socket client=new Socket("127.0.0.1",8000);
            DataOutputStream dataOutputStream = new DataOutputStream( client.getOutputStream());
            dataOutputStream.writeUTF("uploadImages");//sending request to server
            //DataOutputStream out = new DataOutputStream(client.getOutputStream());
            for ( File imageFile : files ) {
                ImageOutputStream ios=ImageIO.createImageOutputStream(client.getOutputStream());
                BufferedImage image=ImageIO.read(imageFile);
                ImageIO.write(image,"PNG",ios);
                //Thread.sleep(100);
            }
        }
        catch (Exception e){}

    }*/
}
