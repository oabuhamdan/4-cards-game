package controllers;

import Objects.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChatRoomController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> messagesList;

    @FXML
    private TextField message;

    @FXML
    void initialize() {
        message.setPromptText("Enter your message here");
    }

    @FXML
    void sendButtonClicked(ActionEvent event) {
        try {
            Socket socket = new Socket("127.0.0.1", 8001);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(Utils.getSignedInUser() + " : " + message.getText());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            List<String> messages = (List<String>) ois.readObject();
            if (messages.size() > 0) {
                ObservableList<String> observableList = FXCollections.observableArrayList(messages);
                FilteredList<String> filteredList = new FilteredList<>(observableList, s -> true);
                messagesList.setItems(filteredList);
            }
            message.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
