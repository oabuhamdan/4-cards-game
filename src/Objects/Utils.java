package Objects;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static String signedInUser;

    private static List<Challenge> challenges = new ArrayList<>();

    public static List<Challenge> getChallenges() {
        return challenges;
    }

    public static void setChallenges(List<Challenge> challenges) {
        Utils.challenges = challenges;
    }

    public static String getSignedInUser() {
        return signedInUser;
    }

    public static void setSignedInUser(String signedInUser) {
        Utils.signedInUser = signedInUser;
    }
}
