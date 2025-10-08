package chat.client;

import javax.swing.*;

public class ConnectorClient2 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatClient::new);
    }
}
