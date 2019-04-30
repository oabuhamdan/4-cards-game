package Server;

import Objects.Challenge;
import Objects.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class MainServer {
    static final int PORT = 8888;
    static List<Challenge> challenges;
    static List<User> users;

    public static void main(String[] args) {
        try {

            ServerSocket s = new ServerSocket(PORT);
            while (true) {
                MyThread t = new MyThread(s.accept());
                loadUsers();
                loadChallenges();
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadUsers() {
        File file = new File("Users.txt");
        try {
            if (file.length() != 0) {
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                users = (List<User>) objectInputStream.readObject();//get users
            } else
                users = new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadChallenges() {
        //fill Utils.challenges List from file ..
        File file = new File("Challenges.txt");
        try {
            if (file.length() != 0) {
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                challenges = (List<Challenge>) objectInputStream.readObject();//get Challenges
            } else
                challenges = new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addChallenge(Challenge challenge) {
        challenges.add(challenge);
    }

    public static List<Challenge> getChallenges() {
        return challenges;
    }
}
