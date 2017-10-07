package chatserver;

public interface Observable {
    public void insert(ChatMessage message);

    public void sendMessage(ChatMessage message);
}
