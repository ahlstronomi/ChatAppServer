package chatserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * AUTHOR: Mikael Ahlström
 * ICT16-M
 * This class reads the user input and does things with
 *
 * METHODS:
 *  run()
 *  userInput()
 *  quitCommand()
 *  usersCommand()
 *  msgsCommand()
 *  setUserName(String name)
 *  helpCommand()
 *  update(ChatMessage m)
 *  commandProcessor()
 *  updateUsers(User u)
 *
 */

class CommandInterpreter implements Runnable, ChatHistoryObserver, UserNameListObserver {

private String command;
    private boolean killSwitch = true;
    private ChatHistory history = ChatHistory.getInstance();
    private InputStream inputStream;
    private PrintStream outputStream;
    private Scanner in;
    private UserNameList userList = UserNameList.getInstance();
    private User user;
    private int userId;


    public CommandInterpreter(InputStream inputStream, PrintStream outputStream) {


        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.in = new Scanner(inputStream);
        this.user = new User("",userList.getUserId());
    }

    // You ask why I have this empty constructor here? Well I don't know either, but it won't work without it :D
    public CommandInterpreter() {
    }


    public void run() {

        try {

            /*
            outputStream.println("\n**** Welcome to ChatApp ****");
            outputStream.println("If you want to start chatting, please enter the command \n!user anyName1 to log in.");
            outputStream.println("After logging in you can send messages with just typing your message and pressing the enter key.");
            outputStream.println("If you want to see all possible commands, type !help \n");

            outputStream.println("Please enter a username: ");
            */


            command = in.nextLine();
            setUserName(command);



            while (killSwitch) {
                userInput();
            }

            inputStream.close();
            userList.removeUser(this.user);
            history.deregister(this);

        } catch (IOException e) {
            e.printStackTrace();
        }





    }


    /**
     * Reads the user input and starts the commandProcessor() -method
     */

    private void userInput() {
        if(in.hasNext()) {
            command = in.nextLine();
            commandProcessor();
        }
    }

    /**
     * COMMAND:
     * Quit the chat and logout.
     * Removes user from the username.
     */

    private void quitCommand() {
        outputStream.println("x¢NOTIFICATION¢x¢Goodbye");
        userList.removeUser(this.user);
        killSwitch = false;
    }

    /**
     * COMMAND:
     * Print out the userlist.
     */

    private void usersCommand() {
        outputStream.println(userList.toString());
    }

    /**
     * COMMAND:
     * Print out the message history
     */

    private void msgsCommand() {
        outputStream.println(history.toString());
    }


    /**
     * COMMAND:
     * Set the username or change it if you already have one.
     * The username can't be longer than 9 characters.
     *
     * @param name Desired username of the client
     */

    private void setUserName(String name) {

        if (!this.user.hasUsername() && name.length() < 10) {
            // Add a new user
            this.user.setUsername(name);
            userList.addUser(this.user);
        } else if (name.length() >= 10) {
            outputStream.println("x¢NOTIFICATION¢x¢Username can't be over 9 characters");
        } else {
            // Change username
            this.user.setUsername(name);
            outputStream.println("x¢NOTIFICATION¢x¢username changed to " + this.user.getUsername());
        }
    }


    /**
     * COMMAND:
     * Print out this very helpful list if you don know what to do
     */

    private void helpCommand() {
        outputStream.println("x¢NOTIFICATION¢x¢**** ChatApp Commands ****");
        outputStream.println("x¢NOTIFICATION¢x¢!quit - logout");
        outputStream.println("x¢NOTIFICATION¢x¢!messages - Show the message history");
        outputStream.println("x¢NOTIFICATION¢x¢!users - Show all users online");
        outputStream.println("x¢NOTIFICATION¢x¢!user yourUserNameHere - Login. If you already have logged in, you can change your username with this command.");
    }

    /**
     * OBSERVER
     * This notifies about new messages that are sent by other users.
     *
     * @param m Message sent by another user.
     */

    public void update(ChatMessage m) {
        outputStream.println(m);
    }

    /**
     * Reads the user input and decides what to do with it
     * if the input starts with a !-character it is interpreted as a command
     */

    private void commandProcessor() {

        String firstChar = "";

        if (!command.isEmpty()) {
            firstChar = String.valueOf(command.charAt(0));
        }
        if (command.equals("!quit")) {
            quitCommand();
        } else if (command.startsWith("!user ")) {
            setUserName(command.substring(6));
        } else if (command.equals("!messages")) {
            msgsCommand();
        } else if (command.equals("!users")) {
            usersCommand();
        } else if (command.equals("!help")) {
            helpCommand();
        } else if (firstChar.equals("!")) {
            outputStream.println("x¢NOTIFICATION¢x¢This is not a valid command. If you need help, type !help");
        } else if (!this.user.hasUsername()) {
            outputStream.println("x¢NOTIFICATION¢x¢Please log in with a command !user yourUserNameHere666");
        } else if (command.isEmpty()) {
            // Don't do anything
        } else {
            ChatMessage msg = new ChatMessage(this.user, command);
            history.insert(msg);
        }

    }

    @Override
    public void updateUsers(User u) {
        outputStream.println("x¢NOTIFICATION¢" + u.getUserId() + "¢" +  u.getUsername() + " Logged in");
    }
}
