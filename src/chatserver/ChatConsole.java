package chatserver;

/**
 * MIKAEL AHLSTRÖM
 * ICT16-M
 * <p>
 * Secret eavesdropping attribute.
 * Print all messages to the IDE-console
 */

public class ChatConsole implements ChatHistoryObserver {

    @Override
    public void update(ChatMessage m) {
        System.out.println("* " + m.getSenderName() + " sent a message *\n " + m);
    }


}
