package Client;

import Objects.Challenge;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainClient extends Application {
    static final int PORT = 8000;
    private static List<Challenge> challenges=new ArrayList<>();
    Socket s ;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxmls/startPage.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
