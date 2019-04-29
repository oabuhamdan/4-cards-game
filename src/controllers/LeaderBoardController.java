package controllers;

import Objects.ClientSocket;
import Objects.LocalDataHandler;
import Objects.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

public class LeaderBoardController {

    private List<User> sortedUsers;



    @FXML
    void initialize() {
        ClientSocket.createNewConnection();
        getLeaderBoard();
    }

    private void getLeaderBoard() {
        try {
            Socket socket = ClientSocket.getInstance();
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            dos.writeUTF("getLeaderBoard");
            sortedUsers = (List<User>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
