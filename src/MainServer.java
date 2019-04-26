import Objects.Challenge;

import javax.sound.sampled.Port;
import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class MainServer {
    static final int PORT = 8000;
    private static List<Challenge> challenges = new ArrayList<>();

    public static void main(String[] args) {
        try {
            loadChallenges();
            ServerSocket s = new ServerSocket(PORT);
            while (true) {
                MyThread t = new MyThread(s.accept(), challenges);
                t.start();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static void loadChallenges() throws IOException, ClassNotFoundException {
        //fill challenges List from file ..
        FileInputStream fileInputStream = new FileInputStream("challenges.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        challenges = (List<Challenge>) objectInputStream.readObject();//get challenges saved on server
    }
}
