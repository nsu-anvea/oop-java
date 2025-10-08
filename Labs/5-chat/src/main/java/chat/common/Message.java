package chat.common;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private final String sender;
    private final String content;
    private final Date timestamp;
    private final MessageType type;

    public Message(String sender, String content, MessageType type) {
        this.sender = sender;
        this.content = content;
        this.timestamp = new Date();
        this.type = type;
    }

    public String getSender() { return sender; }
    public String getContent() { return content; }
    public Date getTimestamp() { return timestamp; }
    public MessageType getType() { return type; }

    public enum MessageType {
        USER_MESSAGE, JOIN_NOTIFICATION, LEAVE_NOTIFICATION, USER_LIST
    }
}