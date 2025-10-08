package chat.common;

public class User {
    private final String nickname;
    private final transient Object outputStream;

    public User(String nickname, Object outputStream) {
        this.nickname = nickname;
        this.outputStream = outputStream;
    }

    public String getNickname() { return nickname; }
    public Object getOutputStream() { return outputStream; }
}