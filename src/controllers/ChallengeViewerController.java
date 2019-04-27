package controllers;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import Objects.Challenge;
import Objects.ClientSocket;
import Objects.LocalDataHandler;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

public class ChallengeViewerController {

    @FXML
    public Label remainingTime;

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

    }

    @FXML
    void howToPlayClicked(ActionEvent event) {

    }

    @FXML
    void leaderBoardClicked(ActionEvent event) {

    }

    @FXML
    void playButtonClicked(ActionEvent event) {

    }

    @FXML
    void signOutClicked(ActionEvent event) {

    }

    @FXML
    void initialize() throws IOException {
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
        Challenge challenge = LocalDataHandler.getChallenges().get(selectedChallenge);
        challenge.setFolderIndex(selectedChallenge);
        BufferedImage[] bufferedImages=new BufferedImage[4];
        Image[] images=new Image[4];
        for(int i = 0 ; i <4 ; i++){
            bufferedImages[i] = ImageIO.read(challenge.getImages().get(i));
            images[i]=SwingFXUtils.toFXImage(bufferedImages[i],null);
        }
        image1.setImage(images[0]);

        image2.setImage(images[1]);

        image3.setImage(images[2]);

        image4.setImage(images[3]);

    }

    private void receiveImages(int selectedFolder) {
        Socket s = ClientSocket.getInstance();
        new File("ClientSideChallenges/" + selectedFolder).mkdir();
        try {
            BufferedInputStream bis = new BufferedInputStream(s.getInputStream());
            DataInputStream dis = new DataInputStream(bis);
            for ( int i = 0; i < 4; i++ ) {
                long fileLength = dis.readLong();
                FileOutputStream fos = new FileOutputStream(new File("ClientSideChallenges/"+selectedFolder+"/image"+i+".png"));
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                for(int j = 0; j < fileLength; j++) bos.write(bis.read());
                bos.close();
            }
            //dis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
