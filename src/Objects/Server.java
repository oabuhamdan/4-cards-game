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
    }

    public static void addUser(User user){
        users.add(user);
    }



}
