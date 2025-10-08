package chat.server;

import java.io.IOException;

public class RunnerChatServer {
    private static final String CONFIG_FILE = "src/main/java/chat/server/" +
            "config/config.properties";

    public static void main(String[] args) throws IOException {
        ChatServer server = new ChatServer(CONFIG_FILE);

        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));

        server.start();
    }
}
