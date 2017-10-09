package chatserver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observer;
import java.util.Set;

/**
 * SINGLETON JAVA CLASS
 * Mikael Ahlström
 * ICT16-M
 *
 * METHODS:
 *  addUser()
 *  removeUser()
 *  toString()
 *  register()
 *  deregister()
 *  logInNotification()
 */

public class UserNameList extends CommandInterpreter {

    private ArrayList<User> userNameList;
    private static UserNameList ourInstance = new UserNameList();
    private Set<UserNameListObserver> observers;
    private int runningUserId = 0;


    public static UserNameList getInstance() {
        return ourInstance;
    }

    private UserNameList() {

        this.userNameList = new ArrayList<>();
        observers = new HashSet<>();

    }

    /**
     * Add a new user to the user list if it doesn't already contain that user.
     *
     * @param u
     * New user
     */

    public void addUser(User u) {
        if (!userNameList.contains(u)) {
            userNameList.add(u);
            logInNotification(u);
        }
    }

    /**
     * Remove the user from the user list. This is done when a user logs out from the chat.
     *
     * @param u
     * User logging out
     */

    public void removeUser(User u) {
        if (userNameList.contains(u)) {
            userNameList.remove(u);
        }
    }

    /**
     * @return
     * Print out the list as a beautiful string.
     */

    public String toString() {
        StringBuilder history = new StringBuilder();
        history.append("x¢NOTIFICATION¢x¢***** USERS ONLINE *****\n");

        for (User u : userNameList) {
            history.append("x¢NOTIFICATION¢x¢" + u.getUsername() + "\n");
        }

        history.append("x¢NOTIFICATION¢x¢************************\n");
        return history.toString();

    }

    public int getUserId(){
        runningUserId++;
        return runningUserId;
    }

    // ----- These methods below are used to notify users when a new user logs in to the chat -----

    public void register(UserNameListObserver o) {
        observers.add(o);
    }

    public void deregister(UserNameListObserver o) {
        observers.remove(o);
    }

    public void logInNotification(User u) {
        for (UserNameListObserver o : observers) {
            o.updateUsers(u);
        }
    }
    // --------------------------------------------------------------------------------------------


}
