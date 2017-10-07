package chatserver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * MIKAEL AHLSTRÖM
 * ICT16-M
 *
 * Methods:
 *  getSenderName()
 *  toString()
 */

public class ChatMessage extends CommandInterpreter {

    private String senderName;
    private int senderId;
    private String message;
    private String timeStamp;

    public ChatMessage(User u, String msg) {
        this.senderId = u.getUserId();
        this.senderName = u.getUsername();
        this.message = msg;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        this.timeStamp = dateFormat.format(new Date());
    }

    public String getSenderName() {
        return this.senderName;
    }

    @Override
    public String toString() {
        String s =this.senderId + "¢" + this.senderName + "¢" + this.timeStamp + "¢" + this.message;
        return s;
    }
}
