package chatserver;

import java.util.ArrayList;
import java.util.Random;

/**
 * MIKAEL AHLSTRÖM
 * ICT16-M
 * <p>
 * Methods:
 * hasUsername();
 * serUsername();
 * getUsername();
 */

public class User extends CommandInterpreter {
    private String username = "";
    private int userId;


    public User(String n, int id) {
        this.username = n;
        this.userId = id;

    }

    public boolean hasUsername() {
        return !username.isEmpty();
    }


    public void setUsername(String n) {
        // Set the desired username and get a random color for it
        this.username = n;
    }

    public String getUsername() {
        return this.username;
    }

    public int getUserId() {
        return this.userId;
    }


}
