package Client;

import Objects.Challenge;
import Objects.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainClient extends Application {
    static final int PORT = 8000;
    private static List<Challenge> challenges=new ArrayList<>();
    Socket s ;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxmls/startPage.fxml"));
        /*InputStream inputStream = new FileInputStream("Server.challenges.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        Server.setChallenges((List<Challenge>) objectInputStream.readObject());
*/
        try {
            s = new Socket("127.0.0.1",PORT);//loopback address should be replaced with the server ip ..
            OutputStream outputStream = s.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeUTF("getChallenges\n");//sending request to server
            //TODO createChallenges();
            InputStream inputStream = s.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            challenges = (List<Challenge>)objectInputStream.readObject();
            Server.setChallenges(challenges);
        } catch (IOException e) {
            e.printStackTrace();
        }

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    void createChallenges() throws IOException {
        InputStream inputStream = s.getInputStream();

    }
    public static void main(String[] args) {
        launch(args);
    }
}
