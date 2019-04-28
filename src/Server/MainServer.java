package Server;

import Objects.Challenge;
import Objects.User;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class MainServer {
    static final int PORT = 8000;
    static List<Challenge> challenges = new ArrayList<>();
    static List<User>users=new ArrayList<>();
    public static void main(String[] args) {
        try {
           // loadChallenges();
            ServerSocket s = new ServerSocket(PORT);
            while (true) {
                MyThread t = new MyThread(s.accept());
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadChallenges() throws IOException, ClassNotFoundException {
        //fill LocalDataHandler.challenges List from file ..
        FileInputStream fileInputStream = new FileInputStream("LocalDataHandler.challenges.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        challenges = (List<Challenge>) objectInputStream.readObject();//get LocalDataHandler.challenges saved on server
    }
}
