package chatserver;

public interface ChatHistoryObserver {
    public void update(ChatMessage m);
}
