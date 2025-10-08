package chat.server;

import chat.common.Message;
import chat.common.User;
import chat.server.config.ServerConfig;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChatServer {
    private final ServerConfig config;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    private static final ExecutorService executor = Executors.newCachedThreadPool();
    private static final List<User> users = new CopyOnWriteArrayList<>();
    private static ServerSocket serverSocket;
    private static PrintWriter logWriter;
    
    public ChatServer(String config) throws IOException {
        this.config = new ServerConfig(config);
    }

    public void start() {
        if (isRunning.get()) {
            throw new IllegalStateException("Server is already running");
        }

        isRunning.set(true);

        try (ServerSocket server = new ServerSocket(config.getPort())) {
            serverSocket = server;
            System.out.println("Server started on port " + config.getPort());

            while (isRunning.get()) {
                Socket socket = server.accept();
                socket.setSoTimeout(config.getTimeout());
                executor.submit(new ClientHandler(socket));
            }
        } catch (IOException e) {
            if (isRunning.get()) {
                System.err.println("Server error: " + e.getMessage());
            }
        } finally {
            System.out.println("Server stopped");
            isRunning.set(false);
        }
    }

    public void stop() {
        if (!isRunning.get()) return;

        System.out.println("Stopping server gracefully...");
        isRunning.set(false);
        executor.shutdown();

        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing server socket: " + e.getMessage());
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket socket;
        private ObjectOutputStream out;
        private ObjectInputStream in;
        private String nickname;

        private final AtomicBoolean isRunning = new AtomicBoolean(true);

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());

                Message joinMessage = (Message) in.readObject();
                this.nickname = joinMessage.getSender();

                User newUser = new User(nickname, out);
                users.add(newUser);

                System.out.println(nickname + " joined the chat");
                broadcast(new Message("Server", nickname + " joined the chat",
                        Message.MessageType.JOIN_NOTIFICATION));

                sendUserList();

                while (true) {
                    try {
                        Message message = (Message) in.readObject();
                        if (message.getType() == Message.MessageType.USER_MESSAGE) {
                            System.out.println(nickname + ": " + message.getContent());
                            broadcast(message);
                        }
                    } catch (SocketTimeoutException e) {
                        if (!isConnectionAlive()) {
                            break;
                        }
                    } catch (EOFException e) {
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error with client " + nickname + ": " + e.getMessage());
            } finally {
                if (nickname != null) {
                    users.removeIf(u -> u.getNickname().equals(nickname));
                    System.out.println(nickname + " left the chat");
                    broadcast(new Message("Server", nickname + " left the chat",
                            Message.MessageType.LEAVE_NOTIFICATION));
                    sendUserList();
                }
                closeResources();
            }
        }

        private boolean isConnectionAlive() {
            return isRunning.get();
        }

        private void closeResources() {
            try { in.close(); } catch (Exception ignored) {}
            try { out.close(); } catch (Exception ignored) {}
            try { socket.close(); } catch (Exception ignored) {}
        }
    }

    private static void broadcast(Message message) {
        for (User user : users) {
            try {
                ObjectOutputStream oos = (ObjectOutputStream) user.getOutputStream();
                oos.writeObject(message);
                oos.flush();
            } catch (IOException e) {
                System.out.println("Error sending message to " + user.getNickname() + ": " + e.getMessage());
            }
        }
    }

    private static void sendUserList() {
        StringBuilder userList = new StringBuilder("Current users: ");
        for (User user : users) {
            userList.append(user.getNickname()).append(", ");
        }
        Message userListMessage = new Message("Server", userList.toString(),
                Message.MessageType.USER_LIST);
        broadcast(userListMessage);
    }
}