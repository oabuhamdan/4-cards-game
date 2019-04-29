package Objects;

import java.io.Serializable;

public class User implements Serializable {
    private String userName;
    private String password;
    private int score;
    private String email;

    public User(String userName, String password, int score, String email) {
        this.userName = userName;
        this.password = password;
        this.score = score;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int value) {
        this.score = score+value;
    }
}
