package chat.client;

import chat.common.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends JFrame {
    private String nickname;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private JList<String> userList;
    private DefaultListModel<String> userListModel;

    public ChatClient() {
        initializeUI();
        connectToServer();
    }

    private void initializeUI() {
        setTitle("Java Serialization Chat");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        inputField.addActionListener(e -> sendMessage());
        sendButton = new JButton("Send");
        sendButton.addActionListener(e -> sendMessage());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);

        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        JScrollPane userScrollPane = new JScrollPane(userList);
        userScrollPane.setPreferredSize(new Dimension(150, 0));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chatScrollPane, userScrollPane);
        splitPane.setDividerLocation(600);

        add(splitPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                disconnectFromServer();
            }
        });
    }

    private void connectToServer() {
        nickname = JOptionPane.showInputDialog(this, "Enter your nickname:");
        if (nickname == null || nickname.trim().isEmpty()) {
            System.exit(0);
        }

        try {
            String serverAddress = JOptionPane.showInputDialog(this,
                    "Enter server address:", "localhost");
            if (serverAddress == null) {
                System.exit(0);
            }

            String stringPort = JOptionPane.showInputDialog(this,
                    "Enter server port:", "5556");
            if (stringPort == null) {
                System.exit(0);
            }
            int port = Integer.parseInt(stringPort);

            socket = new Socket(serverAddress, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            out.writeObject(new Message(nickname, "joined the chat",
                    Message.MessageType.JOIN_NOTIFICATION));
            out.flush();

            new Thread(this::readMessages).start();

            setVisible(true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error connecting to server: " + e.getMessage(),
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void readMessages() {
        try {
            while (true) {
                Message message = (Message) in.readObject();
                SwingUtilities.invokeLater(() -> processMessage(message));
            }
        } catch (IOException | ClassNotFoundException e) {
            SwingUtilities.invokeLater(() -> {
                chatArea.append("Disconnected from server.\n");
                inputField.setEnabled(false);
                sendButton.setEnabled(false);
            });
        }
    }

    private void processMessage(Message message) {
        switch (message.getType()) {
            case USER_MESSAGE:
                chatArea.append(String.format("[%s] %s: %s\n",
                        message.getTimestamp(), message.getSender(), message.getContent()));
                break;
            case JOIN_NOTIFICATION:
            case LEAVE_NOTIFICATION:
                chatArea.append(String.format("[%s] %s\n",
                        message.getTimestamp(), message.getContent()));
                break;
            case USER_LIST:
                updateUserList(message.getContent());
                break;
        }
    }

    private void updateUserList(String userListStr) {
        userListModel.clear();
        String[] parts = userListStr.substring("Current users: ".length()).split(", ");
        for (String user : parts) {
            if (!user.isEmpty()) {
                userListModel.addElement(user);
            }
        }
    }

    public void sendMessage() {
        String text = inputField.getText().trim();
        if (!text.isEmpty()) {
            try {
                out.writeObject(new Message(nickname, text, Message.MessageType.USER_MESSAGE));
                out.flush();
                inputField.setText("");
            } catch (IOException e) {
                chatArea.append("Error sending message: " + e.getMessage() + "\n");
            }
        }
    }

    public void disconnectFromServer() {
        try {
            if (out != null) {
                out.writeObject(new Message(nickname, "left the chat",
                        Message.MessageType.LEAVE_NOTIFICATION));
                out.flush();
            }
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}