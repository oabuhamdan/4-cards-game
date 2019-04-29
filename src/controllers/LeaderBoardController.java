package controllers;

import Objects.ClientSocket;
import Objects.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LeaderBoardController {

    private List<User> sortedUsers;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> leaderBoardList;

    @FXML
    void aboutClicked(ActionEvent event) {

    }

    @FXML
    void closeClicked(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void howToPlayClicked(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert leaderBoardList != null : "fx:id=\"leaderBoardList\" was not injected: check your FXML file 'chooseChallenge.fxml'.";
        ClientSocket.createNewConnection();
        getLeaderBoard();
        List<String> userDesc = new ArrayList<>();
        for ( int i = 0; i < sortedUsers.size(); i++ ) {
            userDesc.add("\t" + sortedUsers.get(i).getUserName() + "\t\t\t" + sortedUsers.get(i).getScore());
        }
        ObservableList<String> observableList = FXCollections.observableArrayList(userDesc);
        FilteredList<String> filteredList = new FilteredList<>(observableList, s -> true);
        leaderBoardList.setItems(filteredList);
    }

    private void getLeaderBoard() {
        try {
            Socket socket = ClientSocket.getInstance();
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("getLeaderBoard");
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            sortedUsers = (List<User>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
