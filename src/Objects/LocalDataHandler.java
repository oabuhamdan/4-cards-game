package Objects;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class LocalDataHandler {

    private static String signedInUser;

    private static List<Challenge> challenges = new ArrayList<>();

    public static List<Challenge> getChallenges() {
        return challenges;
    }


    public static void addChallenge(Challenge challenge) {
        challenges.add(challenge);
        //TODO Serialize LocalDataHandler.challenges object to be sent to actual server
    }

    public static void setChallenges(List<Challenge> challenges) {
        LocalDataHandler.challenges = challenges;
    }


    private void getChallengesFromServer() {
        try {
            Socket s = ClientSocket.getInstance();
            DataOutputStream dataOutputStream = new DataOutputStream(s.getOutputStream());
            dataOutputStream.writeUTF("getChallenges");//sending request to server
            ObjectInputStream objectInputStream = new ObjectInputStream(s.getInputStream());
            challenges = (List<Challenge>) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void setSignedInUser(String signedInUser) {
        LocalDataHandler.signedInUser = signedInUser;
    }

    public static String getSignedInUser() {
        return signedInUser;
    }
}
