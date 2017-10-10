package chatserver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * SINGELTON CLASS
 * MIKAEL AHLSTRÖM
 * ICT16-M
 * <p>
 * METHODS:
 * insert(ChatMessage message)
 * sendMessage(ChatMessage message)
 * register(ChatHistoryObserver o)
 * toString()
 */

public class ChatHistory implements Observable {

    private ArrayList<ChatMessage> chatHistory;
    private static ChatHistory ourInstance = new ChatHistory();
    private Set<ChatHistoryObserver> observers;
    private ChatConsole console = new ChatConsole();


    public static ChatHistory getInstance() {
        return ourInstance;
    }

    private ChatHistory() {
        chatHistory = new ArrayList<ChatMessage>();
        observers = new HashSet<>();
    }

    public void insert(ChatMessage message) {
        chatHistory.add(message);
        sendMessage(message);
    }

    /**
     * Sends the message to all other users in the same chat.
     * @param message Message sent by the user.
     */

    public void sendMessage(ChatMessage message) {
        for (ChatHistoryObserver o : observers) {
            o.update(message);
        }
        console.update(message);
    }

    /**
     * Register a CI as a observer.
     * @param o CI
     */

    public void register(ChatHistoryObserver o) {
        observers.add(o);
    }

    /**
     * Remove the observer from the observer set
     * @param o CI
     */

    public void deregister(ChatHistoryObserver o) {
        observers.remove(o);
    }


    /**
     * @return Print the complete ChatHistory as a beautiful string
     */

    @Override
    public String toString() {

        StringBuilder history = new StringBuilder();
        history.append("x¢NOTIFICATION¢x¢***** CHAT HISTORY *****\n");

        for (ChatMessage m : chatHistory) {
            history.append("x¢NOTIFICATION¢x¢" + m.toString() + "\n");
        }

        history.append("x¢NOTIFICATION¢x¢************************\n");
        return history.toString();

    }

}
