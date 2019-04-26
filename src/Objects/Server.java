package Objects;

import java.util.ArrayList;
import java.util.List;

public class Server {


    private static List<Challenge> challenges=new ArrayList<>();
    private static List<User> users = new ArrayList<>();

    public static List<Challenge> getChallenges() {
        return challenges;
    }

    public static List<User> getUsers() {
        return users;
    }

    public static void addChallenge(Challenge challenge){
        challenges.add(challenge);
        //TODO Serialize Server.challenges object to be sent to actual server
    }

    public static void setChallenges(List<Challenge> challenges) {
        Server.challenges = challenges;
    }

    public static void addUser(User user){
        users.add(user);
        //TODO Serialize users object to be sent to actual server
    }



}
