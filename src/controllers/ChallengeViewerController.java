package controllers;

import Objects.Challenge;
import Objects.ClientSocket;
import Objects.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChallengeViewerController {

    @FXML
    public Label remainingTime;

    @FXML
    public TextField relatedWord;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView image1;

    @FXML
    private ImageView image2;

    @FXML
    private ImageView image3;

    @FXML
    private ImageView image4;

    private Challenge challenge;

    private int minutes;
    private int seconds;

    @FXML
    void aboutClicked(ActionEvent event) {

    }

    @FXML
    void backButtonClicked(ActionEvent event) throws IOException {
        Parent loader = FXMLLoader.load(getClass().getResource("/fxmls/chooseChallenge.fxml"));//Creates a Parent called loader and assign it as ScReen2.FXML
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
        stage.setTitle("LeaderBoard");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void submitButtonClicked(ActionEvent event) throws IOException {
        String enteredText = relatedWord.getText();
        int tookMinutes = Integer.parseInt(remainingTime.getText().substring(0, 1)) - 1 - minutes;//0
        int tookSeconds = 59 - seconds;//
        if (enteredText.equalsIgnoreCase(challenge.getRelatedWord())) {
            JOptionPane.showMessageDialog(null, "Excellent Job !!" +
                    "\nSolved in " + tookMinutes + " minutes and " + tookSeconds + " seconds");
            int score = tookMinutes * 60 + tookSeconds;
            addScoreToUser(score);
        } else {
            JOptionPane.showMessageDialog(null, "That's not correct\nTry again later");
        }

        Parent loader = FXMLLoader.load(getClass().getResource("/fxmls/homePage.fxml"));//Creates a Parent called loader and assign it as ScReen2.FXML
        Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
        app_stage.setScene(scene); //This sets the scene as scene
        app_stage.show(); // this shows the scene
    }

    private void addScoreToUser(int score) {
        ClientSocket.createNewConnection();
        try {
            Socket socket = ClientSocket.getInstance();
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("addScoreToUser");
            dos.writeUTF(Utils.getSignedInUser());
            dos.writeInt(score);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void signOutClicked(ActionEvent event) throws IOException {
        ClientSocket.terminateConnection();
        Parent loader = FXMLLoader.load(getClass().getResource("/fxmls/startPage.fxml"));//Creates a Parent called loader and assign it as ScReen2.FXML
        Scene scene = new Scene(loader); //This creates a new scene called scene and assigns it as the Sample.FXML document which was named "loader"
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //this accesses the window.
        app_stage.setScene(scene); //This sets the scene as scene
        app_stage.show(); // this shows the scene
    }

    @FXML
    void initialize() {
        ClientSocket.createNewConnection();
        assert image1 != null : "fx:id=\"image1\" was not injected: check your FXML file 'chooseChallenge.fxml'.";
        assert image2 != null : "fx:id=\"image2\" was not injected: check your FXML file 'chooseChallenge.fxml'.";
        assert image3 != null : "fx:id=\"image3\" was not injected: check your FXML file 'chooseChallenge.fxml'.";
        assert image4 != null : "fx:id=\"image4\" was not injected: check your FXML file 'chooseChallenge.fxml'.";
    }

    private void getImagesFolder(int selectedFolder) {
        try {
            Socket socket = ClientSocket.getInstance();
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("getImagesFolder");
            dos.writeInt(selectedFolder);
            receiveImages(selectedFolder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setSelectedChallenge(int selectedChallenge) throws IOException {
        getImagesFolder(selectedChallenge);
        challenge = Utils.getChallenges().get(selectedChallenge);
        challenge.setFolderIndex(selectedChallenge);
        Image[] images = new Image[4];
        List<File> imagesFile = challenge.getImages();
        for ( int i = 0; i < 4; i++ ) {
            Image image = new Image(imagesFile.get(i).toURI().toString());
            images[i] = image;
        }
        image1.setImage(images[0]);

        image2.setImage(images[1]);

        image3.setImage(images[2]);

        image4.setImage(images[3]);

        remainingTime.setText(challenge.getTime() + ":00");
        relatedWord.setPromptText("The word has " + challenge.getRelatedWord().length() + " characters");
        new Thread(() -> beginChallenge()).start();
    }

    void beginChallenge() {
        minutes = Integer.parseInt(remainingTime.getText().substring(0, 1));//3
        seconds = Integer.parseInt(remainingTime.getText().substring(2, 4));//59
        while (minutes != 0 || seconds != 0) {
            if (seconds == 0) {
                minutes--;
                seconds = 59;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            seconds--;
        }
    }

    private void receiveImages(int selectedFolder) {
        Socket s = ClientSocket.getInstance();
        new File("ClientSideChallenges/" + selectedFolder).mkdir();
        try {
            BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
            DataInputStream dis = new DataInputStream(bis);
            for ( int i = 0; i < 4; i++ ) {
                long fileLength = dis.readLong();
                FileOutputStream fos = new FileOutputStream(new File("ClientSideChallenges/" + selectedFolder + "/image" + i + ".png"));
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                for ( int j = 0; j < fileLength; j++ ) bos.write(bis.read());
                bos.close();
            }
            //dis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
